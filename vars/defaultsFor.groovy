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
                    stage('Test') {
                        failFast true
                        parallel {
                            stage('Linux') {
                                agent { docker 'maven:3-alpine' }
                                steps {
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
                                    sh 'mvn test -B'
                                }
                                post {
                                    always {
                                        junit testResults: '**/surefire-reports/**/*.xml', allowEmptyResults: true
                                        archiveArtifacts artifacts: '**/*.jar', fingerprint: true
                                    }
                                }
                            }
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
