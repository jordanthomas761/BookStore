pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew assemble bootjar'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Build Docker image') {
            steps {
                sh './gradlew docker'
            }
        }
        stage('Push Docker image') {
            environment {
                DOCKER_HUB_LOGIN = credentials('docker-hub')
            }
            steps {
                sh 'docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
                sh './gradlew dockerPush'
            }
        }
        stage('Deploy Stage') {
            environment {
                BOOKS_ENVIRONMENT = 'stage'
                MYSQL_USERNAME='testbook'
                MYSQL_PASSWORD='xGf2wII0SH3Nks8m#'
            }
            steps {
                sh './gradlew dockerRun -Pargs=18081'
            }
        }
    }
}