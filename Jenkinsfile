node {

   stage('Build') {
     // Run the maven build
     sh "mvn clean package"
   }

   stage('Archive') {
     archiveArtifacts artifacts: 'target/*.jar'
   }

   
}
