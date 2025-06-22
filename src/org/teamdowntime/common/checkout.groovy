package org.teamdowntime.common

class checkout implements Serializable {

    def call(String branch, String repoUrl, String credsId) {
        println "[INFO] Checkout Parameters:"
        println "[INFO] Branch     : ${branch}"
        println "[INFO] Repository : ${repoUrl}"
        println "[INFO] Credentials: ${credsId}"

        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            userRemoteConfigs: [[
                url: repoUrl,
                credentialsId: credsId
            ]]
        ])
    }
}
