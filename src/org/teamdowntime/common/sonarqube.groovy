package org.teamdowntime.common

class sonarqube implements Serializable {
    def steps

    sonarqube(steps) {
        this.steps = steps
    }

    def call(String projectKey, String sonarUrl, String sonarSources, String sonarToken) {
        steps.withCredentials([steps.string(credentialsId: sonarToken, variable: 'SONARQUBE_AUTH_TOKEN')]) {
            steps.sh """
                /opt/sonar-scanner/bin/sonar-scanner \\
                -Dsonar.projectKey=${projectKey} \\
                -Dsonar.sources=${sonarSources} \\
                -Dsonar.host.url=${sonarUrl} \\
                -Dsonar.login=\$SONARQUBE_AUTH_TOKEN
            """
        }
    }
}
