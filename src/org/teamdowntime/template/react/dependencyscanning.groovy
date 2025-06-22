package org.teamdowntime.template.react

import org.teamdowntime.common.owasp
import org.teamdowntime.common.cleanworkspace
import org.teamdowntime.common.checkout
import org.teamdowntime.common.notification

def cleanworkspace = new cleanworkspace()
def checkout = new checkout()

cleanworkspace.call()
checkout.call(branch, url, creds)
def call(Map config) {
    owasp.run(this, config)
}

