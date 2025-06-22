package org.teamdowntime.template.react

import org.teamdowntime.common.sonarqube
import org.teamdowntime.common.checkout
import org.teamdowntime.common.cleanworkspace

class staticcodeanalysis implements Serializable {

    def call(String branch, String url, String creds, String projectKey, String sonarUrl, String sonarSources, String sonarToken) {
        def checkout = new checkout()
        def cleanworkspace = new cleanworkspace()
        def sonarqube = new sonarqube()

        cleanworkspace.call()
        checkout.call(branch, url, creds)
        sonarqube.call(projectKey, sonarUrl, sonarSources, sonarToken)
    }
}
