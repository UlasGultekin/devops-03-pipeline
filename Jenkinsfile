pipeline {
    agent {
        node {
            label 'My-Jenkins-Agent'
        }
    }

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "Maven3"
        jdk 'Java21'
    }
    environment {
        APP_NAME     = 'devops-03-pipeline'
        RELEASE      = '1.0'
        DOCKER_USER  = 'ulasgltkn'
        DOCKER_LOGIN = 'dockerhub'               // Jenkins credentialsId
        IMAGE_TAG    = "${RELEASE}.${BUILD_NUMBER}"
        IMAGE_NAME   = "${DOCKER_USER}/${APP_NAME}"
        JENKINS_API_TOKEN = credentials("JENKINS_API_TOKEN")
        // Tam etiket örneği: "${DOCKER_USER}/${APP_NAME}:${RELEASE}.${BUILD_NUMBER}"
      }

    stages {
        stage('SCM Github') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/UlasGultekin/devops-03-pipeline']]
                )
            }
        }
        stage('Test Maven') {
            steps {
                script {
            if (isUnix()){
            sh 'mvn test'
            }
            else{
            bat 'mvn test'
            }
               }
            }
        }
        stage('Build Maven') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean install'
                    } else {
                        bat 'mvn clean install'
                    }
                }
            }
        }
            stage("SonarQube Analysis") {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') {
                        if (isUnix()) {
                            // Linux or MacOS
                            sh "mvn sonar:sonar"
                        } else {
                            bat 'mvn sonar:sonar'  // Windows
                        }
                    }
                }
            }
        }
            stage("Quality Gate"){
                 steps {
                    script {
                     waitForQualityGate abortPipeline: false, credentialsId: 'jenkins-sonarqube-token'
                }
            }
        }
/*
        stage('Docker Image') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'docker build --tag ulasgltkn/devops-application:latest .'
                    } else {
                        bat 'docker build --tag ulasgltkn/devops-application:latest .'
                    }
                }
            }
        }
*/

        stage('Build & Push Docker Image to DockerHub') {
            steps {
                script {

                    docker.withRegistry('', DOCKER_LOGIN) {

                        docker_image = docker.build "${IMAGE_NAME}"
                        docker_image.push("${IMAGE_TAG}")
                        docker_image.push("latest")
                    }
                }
            }
        }
stage('Cleanup Old Docker Images') {
    steps {
        script {
            if (isUnix()) {
                // Bu repo için tüm image’leri al, tarihe göre sırala, son 3 hariç sil
                sh """
                    docker images "${env.IMAGE_NAME}" --format "{{.Repository}}:{{.Tag}} {{.CreatedAt}}" \\
                      | sort -r -k2 \\
                      | tail -n +4 \\
                      | awk '{print \$1}' \\
                      | xargs -r docker rmi -f
                """
            } else {
                bat """
                    for /f "skip=3 tokens=1" %%i in ('docker images ${env.IMAGE_NAME} --format "{{.Repository}}:{{.Tag}}" ^| sort') do docker rmi -f %%i
                """
            }
        }
    }
}




/*
        stage('Docker Image To DockerHub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub', variable: 'MY_DOCKERHUB_TOKEN')]) {
                        if (isUnix()) {
                            sh 'echo $MY_DOCKERHUB_TOKEN | docker login -u ulasgltkn --password-stdin'
                            sh 'docker push ulasgltkn/devops-application:latest'
                        } else {
                            bat 'echo %MY_DOCKERHUB_TOKEN% | docker login -u ulasgltkn --password-stdin'
                            bat 'docker push ulasgltkn/devops-application:latest'
                        }
                    }
                }
            }
        }

        stage('Deploy Kubernetes') {
            steps {
                script {
                    kubernetesDeploy configs: 'deployment-service.yaml', kubeconfigId: 'kubernetes'
                }
            }
        }
         stage('Docker Image to Clean') {
                    steps {

                           //  sh 'docker image prune -f'
                             bat 'docker image prune -f'

                    }
                }*/

                       stage("Trigger CD Pipeline") {
                            steps {
                                script {
                                    sh "curl -v -k --user admin:${JENKINS_API_TOKEN} -X POST -H 'cache-control: no-cache' -H 'content-type: application/x-www-form-urlencoded' --data 'IMAGE_TAG=${IMAGE_TAG}' 'ec2-13-48-95-121.eu-north-1.compute.amazonaws.com:8080/job/devops-03-pipeline-gitops/buildWithParameters?token=GITOPS_TRIGGER_START'"
                                }
                            }
                        }
    }
}