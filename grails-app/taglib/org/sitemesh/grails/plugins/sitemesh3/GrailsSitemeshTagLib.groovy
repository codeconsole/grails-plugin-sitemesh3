package org.sitemesh.grails.plugins.sitemesh3

class GrailsSitemeshTagLib {

    Closure layoutTitle = { attrs ->
        out << """<sitemesh:write property="title">${attrs.default?:''}</sitemesh:write>""".toString()
    }

    Closure pageProperty = { attrs ->
        out << """<sitemesh:write property="${attrs.name}" />""".toString()
    }

    Closure layoutHead = { attrs, body ->
        StringBuilder tag = new StringBuilder('<sitemesh:write property="head"')
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

    Closure layoutBody = { attrs, body ->
        StringBuilder tag = new StringBuilder('<sitemesh:write property="body"')
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

    Closure content = { attrs, body ->
        StringBuilder tag = new StringBuilder("""<content tag="${attrs.tag}">""")
        tag.append(body())
        tag.append('</content>')
        out << tag.toString()
    }
}
