Better Groovy Scripts:-


    pipeline {
    agent any
    tools {
        maven 'Maven 3.9.x'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/<YOUR_GITHUB_USERNAME>/java-cicd.git'
            }
        }

        stage('Build & Test') {
            steps {
                // Combines building and testing into one efficient step
                sh 'mvn clean package' 
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                // Best practice: Tag with build number instead of just 'latest'
                sh 'docker build -t your-registry/java-cicd:${BUILD_NUMBER} .'
                // sh 'docker push your-registry/java-cicd:${BUILD_NUMBER}'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                    sh 'kubectl get deployments'
                    sh 'kubectl apply -f deployment.yaml'
            }
        }

        stage('Verify Deployment') {
            steps {
                sh 'kubectl rollout status deployment/your-deployment-name'
            }
        }
    }

    post {
        success { echo 'Pipeline executed successfully!' }
        failure { echo 'Pipeline execution failed!' }
    }
}
