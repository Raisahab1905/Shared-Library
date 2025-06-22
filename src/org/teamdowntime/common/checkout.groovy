package org.teamdowntime.common

def call(Map config = [:]) {
    def branch   = config.get('branch', 'main')
    def repoUrl  = config.get('repoUrl', '')
    def credsId  = config.get('credentialsId', '')

    println "[INFO] Cloning from ${repoUrl} on branch ${branch} using credentials ${credsId}"

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
