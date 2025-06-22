package org.teamdowntime.template.react

import org.teamdowntime.common.owasp 

def call(Map config) {
    OwaspDepCheck.run(this, config)
}
