pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
	      sh 'cd docker ; ../sbt -Dprofile=local dockerBuildAndPush'
            }
        }
   }
}
