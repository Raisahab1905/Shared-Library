package org.teamdowntime.common

def call(String projectKey, String sonarUrl, String sonarSources, String sonarToken) {
    // stage('Run SonarQube Analysis') {
        withCredentials([string(credentialsId: sonarToken, variable: 'SONARQUBE_AUTH_TOKEN')]) {
            sh """
                /opt/sonar-scanner/bin/sonar-scanner \
                -Dsonar.projectKey=${projectKey} \
                -Dsonar.sources=${sonarSources} \
                -Dsonar.host.url=${sonarUrl} \
                -Dsonar.login=${sonarToken}
            """
        }
    // }
}
