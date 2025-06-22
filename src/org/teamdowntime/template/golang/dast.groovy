package org.teamdowntime.template.react

import org.teamdowntime.common.*

def call(String targetUrl, String slackChannel, String slackCredId, String emailTo, String buildTrigger) {
    def cleanworkspace = new cleanworkspace()
    def dastRunner = new owaspzap()
    def notifier = new notification(this)

    try {
        cleanworkspace.call()
        dastRunner.run(this, [TARGET_URL: targetUrl])

        notifier.call([
            status: 'SUCCESS',
            buildTrigger: buildTrigger,
            failureReason: '',
            failedStage: '',
            slackChannel: slackChannel,
            slackCredId: slackCredId,
            emailTo: emailTo,
            reportLinks: [
                [name: 'ZAP Report', url: "${env.BUILD_URL}artifact/report.html"]
            ]
        ])

    } catch (Exception e) {
        notifier.call([
            status: 'FAILURE',
            buildTrigger: buildTrigger,
            failureReason: e.message,
            failedStage: env.STAGE_NAME ?: 'Unknown',
            slackChannel: slackChannel,
            slackCredId: slackCredId,
            emailTo: emailTo,
            reportLinks: [
                [name: 'ZAP Report', url: "${env.BUILD_URL}artifact/report.html"]
            ]
        ])
        currentBuild.result = 'FAILURE'
        throw e
    }
}
