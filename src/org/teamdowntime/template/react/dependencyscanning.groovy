package org.teamdowntime.template.react

import org.teamdowntime.common.owasp.groovy

def call(Map config) {
    OwaspDepCheck.run(this, config)
}
