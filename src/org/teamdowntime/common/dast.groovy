package org.teamdowntime.common

def call(Map config = [:]) {
    def ZAP_HOME = config.get('ZAP_HOME', "/var/lib/jenkins/.ZAP-CI")
    def ZAP_DIR = config.get('ZAP_DIR', "/var/lib/jenkins/.ZAP-CI")
    def TARGET_URL = config.get('TARGET_URL', '')
    def ZAP_PORT = config.get('ZAP_PORT', '8092')
    def ZAP_REPORT = "${ZAP_DIR}/report.html"

    try {
        stage('Run ZAP Scan') {
            echo "Running ZAP scan on ${TARGET_URL} using port ${ZAP_PORT}"
            sh """
                cd ${ZAP_DIR}
                chmod +x zap.sh
                ZAP_HOME=${ZAP_HOME} ./zap.sh -cmd \\
                    -port ${ZAP_PORT} \\
                    -quickurl ${TARGET_URL} \\
                    -quickprogress \\
                    -quickout ${ZAP_REPORT}
            """
        }

        stage('Archive ZAP Report') {
            echo "Archiving ZAP report..."
            sh "cp ${ZAP_REPORT} ."
            archiveArtifacts artifacts: 'report.html', allowEmptyArchive: false
        }

        echo "ZAP scan completed successfully."
