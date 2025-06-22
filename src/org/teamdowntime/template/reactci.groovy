package org.teamdowntime.template

// Import all reusable classes
import org.teamdowntimecrew.common.cleanWorkspace
import org.teamdowntimecrew.common.checkout
import org.teamdowntimecrew.common.dast
import org.teamdowntimecrew.common.notification
import org.teamdowntimecrew.common.DastRunner

def call(Map pipelineConfig = [:]) {

    try {
        stage('Clean Workspace') {
            CleanWorkspace.run(this)
        }

        stage('Checkout Code') {
            Checkout.run(this, pipelineConfig.checkout)
        }

        stage('DAST Scan') {
        DastRunner.run(this, pipelineConfig.dast)
       }

        // success notification
        stage('Notify Success') {
            pipelineConfig.notification.status = 'SUCCESS'
            Notification.send(this, pipelineConfig.notification)
        }

    } catch (Exception e) {
        // failure notification
        stage('Notify Failure') {
            pipelineConfig.notification.status = 'FAILURE'
            pipelineConfig.notification.errorMessage = e.message
            Notification.send(this, pipelineConfig.notification)
        }
        currentBuild.result = 'FAILURE'
        throw e
    }
}
