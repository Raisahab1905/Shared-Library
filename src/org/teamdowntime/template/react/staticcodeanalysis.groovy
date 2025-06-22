package org.teamdowntime.template.react

import org.teamdowntime.common.*

    def call(String branch, String url, String creds, String projectKey, String sonarUrl, String sonarSources, String sonarToken) {
        println "[INFO] --- Static Code Analysis Parameters ---"
        println "[INFO] Branch        : ${branch}"
        println "[INFO] Repository URL: ${url}"
        println "[INFO] Credentials   : ${creds}"
        println "[INFO] Sonar Project : ${projectKey}"
        println "[INFO] Sonar URL     : ${sonarUrl}"
        println "[INFO] Sonar Sources : ${sonarSources}"
        println "[INFO] Sonar Token   : ${sonarToken}"

        def checkout = new checkout()
        def cleanworkspace = new cleanworkspace()
        def sonarqube = new sonarqube()

        cleanworkspace.call()
        checkout.call(branch, url, creds)
        sonarqube.call(projectKey, sonarUrl, sonarSources, sonarToken)
    
}
