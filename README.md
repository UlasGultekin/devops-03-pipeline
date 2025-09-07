# 🚀 DevOps Pipeline Kurulum Rehberi

## CI/CD Ekosistemi
```
CI/CD:           Jenkins, Git, GitHub, GitOps, GitHub Actions, GitLab, GitLab CI, Bitbucket, Bamboo
Scripting:       Python, Bash, PowerShell
Containers:      Docker
Orchestration:   Kubernetes, Helm
Cloud:           AWS, Azure, GCP
Virtualization:  VMware, VirtualBox
IaC:             Terraform, Ansible, CloudFormation
Monitoring:      Prometheus, Grafana, ELK
```

---

## 🔧 AWS CLI Kurulumu
[Resmi dökümantasyon](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)

```bash
aws --version
```

### MacOS için:
```bash
ls -la ~/
mv ~/Downloads/MyAWSKeyPair.pem ~/.ssh/
chmod 400 ~/.ssh/MyAWSKeyPair.pem
nano ~/.ssh/config
```

`~/.ssh/config` içine:
```
Host MyDevOpsAWS
  HostName 54.204.235.127
  User ubuntu
  IdentityFile ~/.ssh/MyAWSKeyPair.pem
```

```bash
ssh MyDevOpsAWS
```

---

## 🖥 Jenkins Master Kurulumu

### Güncellemeler:
```bash
sudo apt update
sudo apt upgrade -y
```

### Hostname Değiştirme:
```bash
sudo nano /etc/hostname
# My-Jenkins-Master
sudo reboot
```

### Java 21 Kurulumu:
```bash
sudo apt install openjdk-21-jre -y
java --version
```

### Jenkins Kurulumu:
```bash
sudo wget -O /etc/apt/keyrings/jenkins-keyring.asc https://pkg.jenkins.io/debian/jenkins.io-2023.key
echo "deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian binary/" | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null

sudo apt-get update
sudo apt-get install jenkins -y

sudo systemctl enable jenkins
sudo systemctl start jenkins
sudo systemctl status jenkins
```

Admin şifresini öğren:
```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

Security Group → `8080` portunu açmayı unutma.  
Erişim: `http://PUBLIC_IP:8080/`

---

## ⚙️ Jenkins Agent (Docker İçin)

### Güncellemeler:
```bash
sudo apt update
sudo apt upgrade -y
```

### Hostname:
```bash
sudo nano /etc/hostname
# My-Jenkins-Agent
sudo reboot
```

### Java 21:
```bash
sudo apt install openjdk-21-jre -y
java --version
```

### Docker:
```bash
sudo apt install docker.io -y
sudo usermod -aG docker $USER
sudo reboot
```

---

## 🔑 Master ↔ Agent SSH Key Entegrasyonu

### Master’da:
```bash
ssh-keygen
cd ~/.ssh
cat id_ed25519.pub
```
(çıkan anahtarı kopyala)

### Agent’ta:
```bash
cd ~/.ssh
sudo nano authorized_keys
# Master’dan kopyalanan key buraya eklenir.
```

Sonra makineleri yeniden başlat:
```bash
sudo reboot
```

---

## 📦 SonarQube + PostgreSQL Kurulumu

### PostgreSQL:
```bash
sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'
wget -qO- https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo tee /etc/apt/trusted.gpg.d/pgdg.asc &>/dev/null

sudo apt update
sudo apt install postgresql postgresql-contrib -y

sudo systemctl enable postgresql
sudo systemctl status postgresql
```

Kullanıcı ve DB:
```bash
sudo passwd postgres
su - postgres
createuser sonar
psql
ALTER USER sonar WITH ENCRYPTED password 'sonar';
CREATE DATABASE sonarqube OWNER sonar;
grant all privileges on DATABASE sonarqube to sonar;
\q
exit
```

### Java 17 (Adoptium):
```bash
wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo tee /etc/apt/keyrings/adoptium.asc
echo "deb [signed-by=/etc/apt/keyrings/adoptium.asc] https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | sudo tee /etc/apt/sources.list.d/adoptium.list

sudo apt update
sudo apt install temurin-17-jdk -y
java --version
```

### Kernel Ayarları:
```bash
sudo nano /etc/security/limits.conf
# ekle:
sonarqube   -   nofile   65536
sonarqube   -   nproc    4096

sudo nano /etc/sysctl.conf
# ekle:
vm.max_map_count = 262144
sudo reboot
```

### SonarQube Kurulumu:
```bash
cd /opt
sudo wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-25.9.0.112764.zip
sudo apt install unzip -y
sudo unzip sonarqube-25.9.0.112764.zip -d /opt
sudo mv /opt/sonarqube-25.9.0.112764 /opt/sonarqube

sudo groupadd sonar
sudo useradd -c "user to run SonarQube" -d /opt/sonarqube -g sonar sonar
sudo chown sonar:sonar /opt/sonarqube -R
```

Config dosyası:
```bash
sudo nano /opt/sonarqube/conf/sonar.properties
# ekle:
sonar.jdbc.username=sonar
sonar.jdbc.password=sonar
sonar.jdbc.url=jdbc:postgresql://localhost:5432/sonarqube
```

Service dosyası:
```bash
sudo nano /etc/systemd/system/sonar.service
```
İçine:
```
[Unit]
Description=SonarQube service
After=syslog.target network.target

[Service]
Type=forking
ExecStart=/opt/sonarqube/bin/linux-x86-64/sonar.sh start
ExecStop=/opt/sonarqube/bin/linux-x86-64/sonar.sh stop
User=sonar
Group=sonar
Restart=always
LimitNOFILE=65536
LimitNPROC=4096

[Install]
WantedBy=multi-user.target
```

Başlat:
```bash
sudo systemctl enable sonar
sudo systemctl start sonar
sudo systemctl status sonar
```

Logları takip et:
```bash
sudo tail -f /opt/sonarqube/logs/sonar.log
```

Erişim:  
👉 `http://PUBLIC_IP:9000/`  
**Kullanıcı:** admin  
**Parola:** admin
