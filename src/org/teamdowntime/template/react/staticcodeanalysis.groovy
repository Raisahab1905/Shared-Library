package org.teamdowntime.template.react

import org.teamdowntime.common.*

    def call(String branch, String url, String creds, String projectKey, String sonarUrl, String sonarSources, String sonarToken) {
        def gitCheckOut = new checkout()
        def wsClean = new cleanworkspace()
        def runSonarQubeAnalysis = new sonarqube() // 👈 pass steps

        wsClean.call()
        gitCheckOut.call(branch, url, creds)
        runSonarQubeAnalysis.call(projectKey, sonarUrl, sonarSources, sonarToken)
    }
