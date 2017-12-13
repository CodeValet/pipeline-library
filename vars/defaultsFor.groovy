/**
 * This step applies the default Pipeline for the given langauge definition.
 */

def call(String platform) {
    switch (platform) {
        case 'java':
            pipeline {
                agent {
                    docker {
                    image 'maven:3-alpine'
                    }
                }
                stages {
                    stage('Prepare') {
                        steps {
                            if (true) {
                                echo 'lol'
                            }
                            sh 'mvn -B clean '
                        }
                    }
                    stage('Test') {
                        steps {
                            sh 'mvn test -B'
                            junit testResults: '**/surefire-reports/**/*.xml', allowEmptyResults: true
                            archiveArtifacts artifacts: '**/*.jar', fingerprint: true
                        }
                    }
                }
            }
            break

        default:
            fail "I do not know what the defaults for `${platform}` should be :("
            break
    }
}
