pipeline {
    agent any
    options { timestamps () }
    stages {
        stage('Build') {
            steps {
                script {
                    sh './gradlew build -DbuildNumber=${BUILD_NUMBER} --no-daemon --stacktrace'
                }
            }
        }
        stage('Integration Test') {
             steps {
                script {
                    sh './gradlew createDockerDb -PuseTestData'
                    sh './gradlew integrationTestRK -PuseTestData -DbuildNumber=${BUILD_NUMBER} --stacktrace'
                }
             }
        }
        stage ('Upload to Nexus') {
            when {
                expression { return env.BRANCH_NAME == 'develop' || env.BRANCH_NAME.startsWith('release/') || env.BRANCH_NAME.startsWith('hotfix/') }
            }
            environment {
                NEXUS_CREDS = credentials('Nexus_Pass')
            }
            steps {
                script {
                    sh './gradlew uploadArchives -DnexusServer=$NEXUS_URL -DnexusUsername=$NEXUS_USER -DnexusPassword=${NEXUS_CREDS_PSW} -DbuildNumber=${BUILD_NUMBER} --no-daemon --stacktrace'
                }
            }
        }
    }
    post {
        failure {
            script {
                if (env.BRANCH_NAME == 'develop' || env.BRANCH_NAME.startsWith('release/') || env.BRANCH_NAME.startsWith('hotfix/')) {
                    emailext body: "Something is wrong with ${env.BUILD_URL}",
                    to: "Technology_Crew_Training-DG@wnco.com",
                    subject: "Failed Pipeline: ${currentBuild.fullDisplayName}"
                }
            }
        }
        //Sends email only if build previous build was red and new build is green
        fixed {
            script {
                if (env.BRANCH_NAME == 'develop' || env.BRANCH_NAME.startsWith('release/') || env.BRANCH_NAME.startsWith('hotfix/')) {
                    emailext subject: "Successful Pipeline: ${currentBuild.fullDisplayName}",
                    body: "Previously failed build has passed: ${env.BUILD_URL}",
                    to: "Technology_Crew_Training-DG@wnco.com"
                }
            }
        }
    }
}
