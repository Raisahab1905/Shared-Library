package org.teamdowntime.template.react

import org.teamdowntime.common.owasp
import org.teamdowntime.common.cleanworkspace
import org.teamdowntime.common.checkout
import org.teamdowntime.common.notification

class dependencyscanning implements Serializable {
    def steps

    dependencyscanning(steps) {
        this.steps = steps
    }

    def call(Map config) {
        def cleanworkspace = new cleanworkspace()
        def checkout = new checkout()
        def notify = new notification(steps)

        def branch = config.get('branch', 'main')
        def url = config.get('repoUrl', '')
        def creds = config.get('credentialsId', '')

        try {
            cleanworkspace.call(steps)
            checkout.call(branch, url, creds)
            owasp.run(steps, config)

            notify.call([
                status       : 'SUCCESS',
                emailTo      : config.get('emailTo'),
                slackChannel : config.get('slackChannel'),
                slackCredId  : config.get('slackCredId'),
                buildTrigger : config.get('buildTrigger'),
                reportLinks  : [[name: "OWASP Dependency Report", url: "${steps.env.BUILD_URL}dependency-check-report.html"]]
            ])
        } catch (err) {
            notify.call([
                status        : 'FAILURE',
                emailTo       : config.get('emailTo'),
                slackChannel  : config.get('slackChannel'),
                slackCredId   : config.get('slackCredId'),
                buildTrigger  : config.get('buildTrigger'),
                failureReason : err.message,
                failedStage   : steps.env.STAGE_NAME ?: 'unknown'
            ])
            throw err
        }
    }
}
