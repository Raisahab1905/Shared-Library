package org.teamdowntimecrew.templates

// Import all reusable classes
import org.teamdowntimecrew.common.CleanWorkspace
import org.teamdowntimecrew.common.Checkout
import org.teamdowntimecrew.common.DastRunner
import org.teamdowntimecrew.common.Notification

def call(Map pipelineConfig = [:]) {
    try {
        if (pipelineConfig.cleanworkspace) {
            stage('Clean Workspace') {
                CleanWorkspace.run(this)
            }
        }

        if (pipelineConfig.checkout) {
            stage('Checkout Code') {
                Checkout.run(this, pipelineConfig.checkout)
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
                Notification.send(this, pipelineConfig.notification)
            }
        }

    } catch (Exception e) {
        if (pipelineConfig.notification) {
            stage('Notify Failure') {
                pipelineConfig.notification.status = 'FAILURE'
                pipelineConfig.notification.errorMessage = e.message
                Notification.send(this, pipelineConfig.notification)
            }
        }
        currentBuild.result = 'FAILURE'
        throw e
    }
}
