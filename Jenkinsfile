pipeline {
    agent any
    environment {
        //once you sign up for Docker hub, use that user_id here
        registry = "suti12/user-crud"
        //- update your credentials ID after creating credentials for connecting to Docker Hub
        registryCredential = 'docker_id'
        dockerImage = ''
        DOCKER_TAG = getVersion()
    }
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/SutirthaSaha/user-crud.git'
            }
        }

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
                // sh 'docker rm -f user_crud'
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