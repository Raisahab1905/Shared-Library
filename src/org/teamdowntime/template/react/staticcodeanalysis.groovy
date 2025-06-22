package org.teamdowntime.template.react

import org.teamdowntime.common.*

class staticcodeanalysis implements Serializable {
    def steps

    staticcodeanalysis(steps) {
        this.steps = steps
    }

    def run(String branch, String url, String creds, String projectKey, String sonarUrl, String sonarSources, String sonarToken) {
        def gitCheckOut = new checkout()
        def wsClean = new cleanworkspace()
        def runSonarQubeAnalysis = new sonarqube(steps) // 👈 pass steps

        wsClean.call()
        gitCheckOut.call(branch, url, creds)
        runSonarQubeAnalysis.call(projectKey, sonarUrl, sonarSources, sonarToken)
    }
}
