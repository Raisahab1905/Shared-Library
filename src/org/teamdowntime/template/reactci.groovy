package org.teamdowntime.template

// Import reusable classes and methods
import org.teamdowntime.common.*

def call(Map pipelineConfig = [:]) {
    try {
        if (pipelineConfig.cleanworkspace) {
            stage('Clean Workspace') {
                cleanworkspace()
            }
        }

        if (pipelineConfig.checkout) {
            stage('Checkout Code') {
                checkout(pipelineConfig.checkout)
            }
        }

        if (pipelineConfig.dast) {
                DastRunner.run(this, pipelineConfig.dast)
        }

        if (pipelineConfig.notification) {
            stage('Notify Success') {
                pipelineConfig.notification.status = 'SUCCESS'
                new notification(this).call(pipelineConfig.notification)
            }
        }

    } catch (Exception e) {
        if (pipelineConfig.notification) {
            stage('Notify Failure') {
                pipelineConfig.notification.status = 'FAILURE'
                pipelineConfig.notification.failureReason = e.message
                pipelineConfig.notification.failedStage = env.STAGE_NAME
                new notification(this).call(pipelineConfig.notification)
            }
        }
        currentBuild.result = 'FAILURE'
        throw e
    }
}
