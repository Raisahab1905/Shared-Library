package org.teamdowntime.template.react

import org.teamdowntime.common.*

def call(Map config) {
    def depChecker = new owaspdepcheck()
    depChecker.call(this, config)
}
