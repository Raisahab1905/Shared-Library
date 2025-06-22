package org.teamdowntime.template.react

import org.teamdowntime.common.OwaspDepCheck

def call(Map config) {
    OwaspDepCheck.run(this, config)
}
