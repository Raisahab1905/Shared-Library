package org.teamdowntime.common

class sonarqube implements Serializable {

    def call(String projectKey, String sonarUrl, String sonarSources, String sonarTokenId) {
        // sonarTokenId is the Jenkins credentialsId
        def sonarToken = sh(
            script: "echo \$(cat /tmp/sonar_token)", // this will be replaced by Jenkins credentials binding
            returnStdout: true
        ).trim()

        sh """
            /opt/sonar-scanner/bin/sonar-scanner \\
            -Dsonar.projectKey=${projectKey} \\
            -Dsonar.sources=${sonarSources} \\
            -Dsonar.host.url=${sonarUrl} \\
            -Dsonar.login=${sonarToken}
        """
    }
}
