pipeline {
stages {
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
		checkout scm
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.
	   
	   steps {	   
      mvnHome = tool 'M3'
      gradleHome = tool 'gradle'
	   }
   }
   stage('Build') {
      // Run the maven build
	   steps {
		         mvnHome = tool 'M3'
      gradleHome = tool 'gradle'
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
      }
	   }
   }
   stage('gradle build') {
	   steps {
		         mvnHome = tool 'M3'
      gradleHome = tool 'gradle'
        if (isUnix()){
		//sh "'${gradleHome}/bin/gradle clean build'"
		sh "./gradlew clean build"
		} else {
		   bat(/"${gradleHome}\bin\gradle" clean build/)
		   }
	   }
		   }
	   
}
}
