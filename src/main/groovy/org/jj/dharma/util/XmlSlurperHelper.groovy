package org.jj.dharma.util

import groovy.util.slurpersupport.GPathResult
import org.ccil.cowan.tagsoup.Parser

class XmlSlurperHelper {

    static Collection findAll(String uri, Closure closure) {
        GPathResult htmlContent = new XmlSlurper(new Parser()).parse(uri)

        return htmlContent.'**'.findAll { GPathResult gPathResult ->
            closure(gPathResult)
        }
    }

}
