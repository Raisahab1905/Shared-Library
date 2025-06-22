package org.teamdowntime.template.react

import org.teamdowntime.common.owasp
import org.teamdowntime.common.cleanworkspace
import org.teamdowntime.common.checkout

def call(Map config) {
    def cleanworkspace = new cleanworkspace()
    def checkout = new checkout()

    // Extract values from config
    def branch = config.get('branch', 'main')
    def url = config.get('repoUrl', '')
    def creds = config.get('credentialsId', '')

    cleanworkspace.call(this)
    checkout.call(this, branch, url, creds)

    owasp.run(this, config)
}
