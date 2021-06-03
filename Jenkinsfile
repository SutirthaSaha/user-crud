pipeline {
    agent any
    environment {
        //once you sign up for Docker hub, use that user_id here
        registry = "suti12/user-crud"
        //- update your credentials ID after creating credentials for connecting to Docker Hub
        registryCredential = 'docker_id'
        dockerImage = ''
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
                script {
                    dockerImage = docker.build registry
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Deploy to Test Server') {
            steps {
                sh 'docker ps -f name=user_crud -q | xargs --no-run-if-empty docker container stop'
                sh 'docker container ls -a -fname=user_crud -q | xargs -r docker container rm'
                script {
                    dockerImage.run("-p 8090:8090 --name user_crud")
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
                ansiblePlaybook credentialsId: 'linux_cred', disableHostKeyChecking: true, installation: 'ansible', inventory: 'dev.inv', playbook: 'deploy.yml'
            }
        }
    }
}