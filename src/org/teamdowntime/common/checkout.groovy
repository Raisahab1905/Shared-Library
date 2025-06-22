package org.teamdowntime.common

def call(Map config = [:]) {
    def branch     = config.get('branch', 'main')
    def repoUrl    = config.get('repoUrl', '')
    def credsId    = config.get('credentialsId', '')

    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [[
            url: repoUrl,
            credentialsId: credsId
        ]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        submoduleCfg: []
    ])
}
