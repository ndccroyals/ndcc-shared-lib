#!/usr/bin/env groovy
node {	
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git'https://github.com/ndccroyals/ndcc-service-registration.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.            
   }
