package org.teamdowntime.template.react

import org.teamdowntime.common.*

def call(String branch, String url, String creds, String projectKey, String sonarUrl, String sonarSources, String sonarToken) {
    def gitCheckOut = new gitCheckOut()
    def wsClean = new wsClean()
    def installDependencies = new installDependencies()
    def runSonarQubeAnalysis = new runSonarQubeAnalysis()

    wsClean.call()
    gitCheckOut.call(branch, url, creds)
    installDependencies.call()
    runSonarQubeAnalysis.call(projectKey, sonarUrl, sonarSources, sonarToken)
}
