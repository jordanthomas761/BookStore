pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew assemble'
            }
        }
        stage('Test') {
            environment {
                BOOKS_ENVIORMENT = 'dev'
            }
            steps {
                sh './gradlew test'
            }
        }
    }
}