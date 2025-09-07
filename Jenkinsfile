pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "Maven3"
        jdk 'Java21'
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
            if (isUnix()){
            sh 'mvn test'
            }
            else{
            bat 'mvn test'
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
    }
}