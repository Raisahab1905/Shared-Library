package org.teamdowntime.template.react

import org.teamdowntime.common.*

def call(Map config) {
    OwaspDepChecker.run(this, config)
}
