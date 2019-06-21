#!/usr/bin/env groovy
node {
   //def mvnHome
   //def gradleHome
   //def dockerHome
	
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git'https://github.com/ndccroyals/ndcc-service-registration.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'M3'
      gradleHome = tool 'gradle'
      dockerHome = tool 'DOCKER_HOME'	   
   }
   stage('Build') {
      // Run the maven build
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
	      
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
      }
   }
   stage('gradle build') {
        if (isUnix()){
		//sh "'${gradleHome}/bin/gradle clean'"
		sh "./gradlew clean build"
		sh "sudo /usr/local/bin/docker build -t ndcc ."
		} else {
		   bat(/"${gradleHome}\bin\gradle" clean build/)
		   }
		   }
		   
     // junit '**/target/surefire-reports/TEST-*.xml'
     // archive 'target/*.jar'
  // }
   
}
