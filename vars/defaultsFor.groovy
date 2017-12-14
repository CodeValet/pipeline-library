/**
 * This step applies the default Pipeline for the given langauge definition.
 */

def call(String platform) {
    switch (platform) {
        case 'maven':
            pipeline {
                agent none
                options {
                    buildDiscarder(logRotator(numToKeepStr: '10'))
                    timeout(time: 1, unit: 'HOURS')
                }
                stages {
                    stage('Debian Linux') {
                        agent { docker 'maven:slim' }
                        steps {
                            checkout scm
                            sh 'mvn test -B'
                        }
                        post {
                            always {
                                junit testResults: '**/surefire-reports/**/*.xml', allowEmptyResults: true
                                archiveArtifacts artifacts: '**/*.jar', fingerprint: true
                            }
                        }
                    }
                    stage('Alpine Linux') {
                        agent { docker 'maven:3-alpine' }
                        steps {
                            checkout scm
                            sh 'mvn test -B'
                        }
                        post {
                            always {
                                junit testResults: '**/surefire-reports/**/*.xml', allowEmptyResults: true
                                archiveArtifacts artifacts: '**/*.jar', fingerprint: true
                            }
                        }
                    }
                    stage('FreeBSD 11') {
                        agent { label 'freebsd' }
                        steps {
                            echo 'Code Valet does not currently support Maven on FreeBSD'
                        }
                    }
                }
            }
            return
            break

        default:
            fail "I do not know what the defaults for `${platform}` should be :("
            break
    }
}
