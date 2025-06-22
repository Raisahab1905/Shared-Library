package org.teamdowntime.template

// Import all reusable classes
import org.teamdowntimecrew.common.cleanWorkspace
import org.teamdowntimecrew.common.checkout
import org.teamdowntimecrew.common.DastRunner
import org.teamdowntimecrew.common.notification

def call(Map pipelineConfig = [:]) {
    try {
        if (pipelineConfig.cleanworkspace) {
            stage('Clean Workspace') {
                cleanWorkspace.run(this)
            }
        }

        if (pipelineConfig.checkout) {
            stage('Checkout Code') {
                checkout.run(this, pipelineConfig.checkout)
            }
        }

        if (pipelineConfig.dast) {
            stage('DAST Scan') {
                DastRunner.run(this, pipelineConfig.dast)
            }
        }

        if (pipelineConfig.notification) {
            stage('Notify Success') {
                pipelineConfig.notification.status = 'SUCCESS'
                notification.send(this, pipelineConfig.notification)
            }
        }

    } catch (Exception e) {
        if (pipelineConfig.notification) {
            stage('Notify Failure') {
                pipelineConfig.notification.status = 'FAILURE'
                pipelineConfig.notification.errorMessage = e.message
                notification.send(this, pipelineConfig.notification)
            }
        }
        currentBuild.result = 'FAILURE'
        throw e
    }
}
