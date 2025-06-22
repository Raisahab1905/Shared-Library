package org.teamdowntime.template.react

import org.teamdowntime.common.*

def call(Map config) {
    OwaspDepCheck.run(this, config)
}
