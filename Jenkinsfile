pipeline {
    agent any
    triggers {
        cron('H H/4 * * * *') // Runs every 4 minutes
    }

    environment {
        REGISTRY_CREDENTIALS = 'dockerhub-credentials'
        KUBECONFIG_CREDENTIALS = 'kubeconfig-credentials'
        IMAGE_NAME = 'lucifer1124/k8s-automated-pipeline'
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Fetch Code') {
            steps {
                checkout scm
            }
        }

        stage('Maven Test & Package') {
            steps {
                sh 'mvn clean test package'
            }
        }

        stage('Build Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -t ${IMAGE_NAME}:latest ."
            }
        }

        stage('Push Image Layers') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${REGISTRY_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin"
                    sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker push ${IMAGE_NAME}:latest"
                }
            }
        }

        stage('Apply Kubernetes Update') {
            steps {
                withCredentials([file(credentialsId: "${KUBECONFIG_CREDENTIALS}", variable: 'KUBE_CONFIG_PATH')]) {
                    // Replaces the placeholder tracking tag inside the deployment file with the immutable build number
                    sh "sed -i 's|:latest|:${IMAGE_TAG}|g' k8s-deployment.yaml"
                    
                    sh "kubectl apply -f k8s-deployment.yaml --kubeconfig=${KUBE_CONFIG_PATH}"
                }
            }
        }
    }

    post {
        always {
            sh "docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || true"
        }
    }
}