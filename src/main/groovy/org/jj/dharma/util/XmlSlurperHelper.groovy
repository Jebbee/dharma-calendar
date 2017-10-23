package org.jj.dharma.util

import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChild
import org.ccil.cowan.tagsoup.Parser

class XmlSlurperHelper {

    static NodeChild find(String uri, Closure closure) {
        GPathResult htmlContent = getHtmlContent(uri)

        return (NodeChild) htmlContent.'**'.find { GPathResult gPathResult ->
            closure(gPathResult)
        }
    }

    static Collection<NodeChild> findAll(String uri, Closure closure) {
        GPathResult htmlContent = getHtmlContent(uri)

        return htmlContent.'**'.findAll { GPathResult gPathResult ->
            closure(gPathResult)
        }
    }

    static GPathResult getHtmlContent(String uri) {
        return new XmlSlurper(new Parser()).parse(uri)
    }

}
