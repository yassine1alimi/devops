pipeline {
    agent any
   tools {
        maven 'maven'
        jdk 'jdk'
    }
    stages {

        stage('clone git repo'){
            steps {
                bat "if exist build rmdir /s /q build"
                bat "git clone https://github.com/ihebrejeb/demo.git ./build"
            }
        }
       
         stage('sonar'){
            steps {
                dir("build"){
                    bat "mvn sonar:sonar"
                }
            }
        }
        
        stage("mvn test") {
            steps {
                dir("build"){
                    bat "mvn test"
                }
            }
        }
     stage("mvn package") {
            steps {
                dir("build"){
                    bat "mvn package -Dmaven.test.skip"
                }
            }
        }
        stage("Deployment stage") {
            steps {
                dir('build') {
                    bat "mvn deploy"
                }
            }
        }    
         
        stage('Building docker image') {
          steps{
                dir("build") {
                    bat "docker build -f Dockerfile -t app.jar ."
                }
            }    
        }
            
        stage('Run docker image') {
            steps{
                dir("build") {
                    bat "docker run -d -p 8088:8088 app.jar"
                }
            }    
        }
          
       
    }
}