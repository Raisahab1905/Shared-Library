package org.teamdowntime.common

class OwaspDepCheck {
    static void run(context, Map config = [:]) {
        def reportDir     = config.get('reportDir', 'dependency-report')
        def reportHtml    = config.get('reportHtml', 'dependency-check-report.html')
        def cacheDir      = config.get('cacheDir', '.dep-check-cache')
        def repoUrl       = config.get('repoUrl', '')
        def branch        = config.get('branch', 'main')
        def credentialsId = config.get('credentialsId', '')
        def projectName   = config.get('projectName', 'DefaultProject')

        context.echo "[INFO] --- Dependency Check Configuration ---"
        context.echo "[INFO] Repo URL         : ${repoUrl}"
        context.echo "[INFO] Branch           : ${branch}"
        context.echo "[INFO] Git Credentials  : ${credentialsId}"
        context.echo "[INFO] Project Name     : ${projectName}"
        context.echo "[INFO] Report Directory : ${reportDir}"
        context.echo "[INFO] Report File      : ${reportHtml}"
        context.echo "[INFO] Cache Directory  : ${cacheDir}"

        context.stage('Clean Workspace') {
            context.cleanWs()
        }

        context.stage('Checkout Code') {
            context.git url: repoUrl, branch: branch, credentialsId: credentialsId
        }

        context.stage('Run OWASP Dependency Check') {
            context.withCredentials([context.string(credentialsId: 'owasp-api', variable: 'NVD_API_KEY')]) {
                context.sh """
                    mkdir -p ${reportDir}
                    /opt/dependency-check/bin/dependency-check.sh \\
                        --project '${projectName}' \\
                        --scan . \\
                        --format HTML \\
                        --out ${reportDir} \\
                        --data ${cacheDir} \\
                        --nvdApiKey=\${NVD_API_KEY}
                """
            }
        }

        context.stage('Archive and Publish Report') {
            context.archiveArtifacts artifacts: "${reportDir}/${reportHtml}", allowEmptyArchive: true

            context.publishHTML(
                reportDir: "${reportDir}",
                reportFiles: "${reportHtml}",
                reportName: 'OWASP Dependency Report',
                allowMissing: false,
                keepAll: true,
                alwaysLinkToLastBuild: true
            )
        }
    }
}

