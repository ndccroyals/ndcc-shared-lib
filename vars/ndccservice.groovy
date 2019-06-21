pipeline {
stages {
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
		
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.
	   
	   steps {	   
		   println 'test here'
	   }
   }
}
}
