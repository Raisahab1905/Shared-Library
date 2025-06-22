package org.teamdowntime.template.react

import org.teamdowntime.common.owasp
import org.teamdowntime.common.checkout
import org.teamdowntime.common.notification

def call(Map config) {
    def branch = config.get('branch', 'main')
    def url = config.get('repoUrl', '')
    def creds = config.get('credentialsId', '')

    cleanWs()

    // ✅ Use named arguments map
    checkout([
        branch: branch,
        repoUrl: url,
        credentialsId: creds
    ])

    owasp.run(this, config)

    def notifier = new notification(this)
    notifier.call(config)
}
