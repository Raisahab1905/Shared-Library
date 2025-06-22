package org.teamdowntime.template.react

import org.teamdowntime.common.owasp

def call(Map config) {
    // Extract values from config
    def branch = config.get('branch', 'main')
    def url = config.get('repoUrl', '')
    def creds = config.get('credentialsId', '')

    // These are global script steps (defined using def call()), so no need to instantiate
    cleanworkspace()
    checkout(branch, url, creds)

    owasp.run(this, config)
}
