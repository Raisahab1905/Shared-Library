package org.teamdowntime.common

    def call(String branch, String repoUrl, String credsId) {

        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            userRemoteConfigs: [[
                url: repoUrl,
                credentialsId: credsId
            ]]
        ])
    }
