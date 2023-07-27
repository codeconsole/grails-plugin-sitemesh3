package org.sitemesh.grails.plugins.sitemesh3

class SitemeshTagLib {
    static namespace = "sitemesh"

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
