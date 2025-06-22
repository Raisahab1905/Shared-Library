package org.teamdowntime.template.react

import org.teamdowntime.common.owasp
import org.teamdowntime.common.notification  // ✅ class needs import

def call(Map config) {
    def branch = config.get('branch', 'main')
    def url = config.get('repoUrl', '')
    def creds = config.get('credentialsId', '')

    cleanworkspace()
    checkout(branch, url, creds)

    owasp.run(this, config)

    // ✅ Notify via Slack and Email
    def notifier = new notification(this)
    notifier.call(config)
}
