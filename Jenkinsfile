pipeline {
    agent any
    environment {
        registry = "suti12/user-crud"
        DOCKER_TAG = getVersion()
    }
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Build and Test') {
            steps{
                sh "mvn clean install"
            }
        }

        stage('Build and Upload Image to Repo') {
            steps{
                sh "docker build . -t suti12/user-crud:${DOCKER_TAG} "
                withCredentials([string(credentialsId: 'docker-hub-pwd', variable: 'docker_pwd')]) {
                    sh "docker login -u suti12 -p ${docker_pwd}"
                }
                sh "docker push suti12/user-crud:${DOCKER_TAG} "
            }
        }

        stage('Static Code Analysis') {
            steps{
                sh 'mvn sonar:sonar -Dsonar.projectKey=user-crud -Dsonar.host.url=http://18.188.44.205:9000 -Dsonar.login=ffd0c7c0b025ac9c814afc1770aedfcc6ddc1c59'
            }
        }

        stage('Deploy to Test Server') {
            steps {
                sh 'docker ps -f name=user_crud -q | xargs --no-run-if-empty docker container stop'
                sh 'docker container ls -a -fname=user_crud -q | xargs -r docker container rm'
                script {
                    sh "docker run -d -p 8090:8090 --name user_crud suti12/user-crud"
                }
            }
        }

        stage('Acceptance Stage') {
            steps {
                input 'Approved ?'
            }
        }

        stage('Deploy to Prod Server') {
            steps {
                ansiblePlaybook credentialsId: 'linux_cred', disableHostKeyChecking: true, extras: "-e DOCKER_TAG=${DOCKER_TAG}", installation: 'ansible', inventory: 'dev.inv', playbook: 'deploy.yml'
            }
        }
    }
}

def getVersion(){
    def commitHash = sh label: '', returnStdout: true, script: 'git rev-parse --short HEAD'
    return commitHash
}