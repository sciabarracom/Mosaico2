pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
	             sh 'cd plugin ; ../sbt -no-colors publishLocal ; cd ../docker ; ../sbt -Dprofile=localhost -no-colors minideb/dockerBuildAndPush'
            }
        }
   }
}
