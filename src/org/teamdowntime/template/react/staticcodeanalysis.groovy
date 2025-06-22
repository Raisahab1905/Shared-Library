package org.teamdowntime.template.react

import org.teamdowntime.common.*

class staticcodeanalysis implements Serializable {

    def call(String branch, String url, String creds, String projectKey, String sonarUrl, String sonarSources, String sonarToken) {
        def checkout = new checkout()
        def cleanworkspace = new cleanworkspace()
        def sonarqube = new sonarqube()

        checkout.call(branch, url, creds)
        cleanworkspace.call()
        sonarqube.call(projectKey, sonarUrl, sonarSources, sonarToken)
    }
}
