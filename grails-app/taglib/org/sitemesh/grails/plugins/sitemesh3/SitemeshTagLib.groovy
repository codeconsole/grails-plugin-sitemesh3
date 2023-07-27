package org.sitemesh.grails.plugins.sitemesh3

class SitemeshTagLib {
    static namespace = "sitemesh"

    // Since org/grails/plugins/web/taglib/SitemeshTagLib.groovy exists
    // Grails will throw an exception because there is no write tag.
    // This is here to temporarily handle the exception.  All it does is
    // just write the same tag.
    def write = { attrs, body ->
        StringBuilder tag = new StringBuilder('<sitemesh:write ')
        attrs.each {
            tag.append("""${it.key}="${it.value}" """)
        }
        String bodyContent = body()
        if (bodyContent) {
            tag.append('>')
            tag.append(bodyContent)
            tag.append('</sitemesh:write>')
        } else {
            tag.append('/>')
        }
        out << tag.toString()
    }

    // Grails org.grails.gsp.compiler.SitemeshPreprocessor replaces
    // <content /> tags with <sitemesh:captureContent /> tags. This just changes them back.
    // Alternatively, a 'sitemesh:captureContent' ContentBlockExtractingRule can be used.
    // See: GrailsTagRuleBundle
    def captureContent = { attrs, body ->
        StringBuilder tag = new StringBuilder('<content ')
        attrs.each {
            tag.append("""${it.key}="${it.value}" """)
        }
        tag.append('>')
        tag.append(body())
        tag.append('</content>')
        out << tag.toString()
    }
}
