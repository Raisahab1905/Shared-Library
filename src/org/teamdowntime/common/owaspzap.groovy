package org.teamdowntime.common

class DastRunner {
    static void run(context, Map config = [:]) {
        def ZAP_HOME = config.get('ZAP_HOME', "/var/lib/jenkins/.ZAP-CI")
        def ZAP_DIR = config.get('ZAP_DIR', "/var/lib/jenkins/.ZAP-CI")
        def TARGET_URL = config.get('TARGET_URL', '')
        def ZAP_PORT = config.get('ZAP_PORT', '8092')
        def ZAP_REPORT = "${ZAP_DIR}/report.html"

        if (!TARGET_URL?.trim()) {
            context.error("TARGET_URL is required for DAST scan")
        }

        context.echo "Running ZAP scan on ${TARGET_URL} using port ${ZAP_PORT}"
        context.sh """
            cd ${ZAP_DIR}
            chmod +x zap.sh
            ZAP_HOME=${ZAP_HOME} ./zap.sh -cmd \\
                -port ${ZAP_PORT} \\
                -quickurl ${TARGET_URL} \\
                -quickprogress \\
                -quickout ${ZAP_REPORT}
        """

        context.echo "Archiving ZAP report..."
        context.sh "cp ${ZAP_REPORT} ."
        context.archiveArtifacts artifacts: 'report.html', allowEmptyArchive: false

        context.echo "ZAP scan completed successfully."
    }
}
