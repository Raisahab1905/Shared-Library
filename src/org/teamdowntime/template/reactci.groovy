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
            cleanWorkspace.run(this)
        }

        stage('Checkout Code') {
            checkout.run(this, pipelineConfig.checkout)
        }

        stage('DAST Scan') {
        dastrunner.run(this, pipelineConfig.dast)
       }

        // success notification
        stage('Notify Success') {
            pipelineConfig.notification.status = 'SUCCESS'
            notification.send(this, pipelineConfig.notification)
        }

    } catch (Exception e) {
        // failure notification
        stage('Notify Failure') {
            pipelineConfig.notification.status = 'FAILURE'
            pipelineConfig.notification.errorMessage = e.message
            notification.send(this, pipelineConfig.notification)
        }
        currentBuild.result = 'FAILURE'
        throw e
    }
}
