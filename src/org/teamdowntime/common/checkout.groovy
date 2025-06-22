package org.teamdowntimecrew.common

def call(Map config = [:]) {
    def repoName = config.repoName ?: error("Missing repoName")
    def branch = config.branch ?: 'main'
    def url = config.url ?: error("Missing Git repo URL")
    def credentialsId = config.credentialsId ?: error("Missing credentialsId")

    dir(repoName) {
        git branch: branch, credentialsId: credentialsId, url: url
    }
}
