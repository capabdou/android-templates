pipeline {
  agent { 
    node { label 'android' }                     (*)
  }
  stages {                                       (**)
    stage('Lint & Unit Test') {
      parallel {                                 (***)
        stage('checkStyle') {
          steps {
            // We use checkstyle gradle plugin to perform this
            sh './gradlew checkStyle'
          }
        }

        stage('Unit Test') {
          steps {
            // Execute your Unit Test
            sh './gradlew testStagingDebug'
          }
        }
      }
    }
    stage('UI Testing') {
      steps {
        script {                                  (****)                          
          if (currentBuild.result == null         
              || currentBuild.result == 'SUCCESS') {  
          // Start your emulator, testing tools
          sh 'emulator @Nexus_Emulator_API_24
          sh 'appium &'  
     
          // You're set to go, now execute your UI test
          sh 'rspec spec -fd'
          }
        }
      }
    }

  post {                                           (*****)
    always {
      archiveArtifacts(allowEmptyArchive: true, artifacts: 'app/build/outputs/apk/production/release/*.apk')
      // And kill the emulator?
      sh 'adb emu kill'
    }
  }
}