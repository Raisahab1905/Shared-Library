package org.teamdowntime.template.react

import org.teamdowntime.common.sonarqube
import org.teamdowntime.common.checkout
import org.teamdowntime.common.cleanworkspace

class staticcodeanalysis implements Serializable {

    def call(String branch, String url, String creds, String projectKey, String sonarUrl, String sonarSources, String sonarToken) {
        def gitCheckOut = new checkout()
        def wsClean = new cleanworkspace()
        def sonar = new sonarqube()

        wsClean.call()
        gitCheckOut.call(branch, url, creds)
        sonar.call(projectKey, sonarUrl, sonarSources, sonarToken)
    }
}
