package org.grails.plugins.web.taglib

import org.grails.encoder.Encoder
import org.grails.exceptions.ExceptionUtils
import org.grails.web.errors.ErrorsViewStackTracePrinter
import org.grails.web.gsp.GroovyPagesTemplateRenderer
import org.grails.web.util.WebUtils
import org.springframework.http.HttpStatus
import org.springframework.util.StringUtils

class RenderTagLib {

    GroovyPagesTemplateRenderer groovyPagesTemplateRenderer
    ErrorsViewStackTracePrinter errorsViewStackTracePrinter

    Closure applyLayout = { Map attrs, body -> }

    Closure pageProperty = { attrs ->
        out << """<sitemesh:write property="${attrs.name}" />""".toString()
    }

    Closure ifPageProperty = { Map attrs, body ->
        throw new RuntimeException("Not Implemented. Please use pageProperty instead.")
    }

    Closure layoutTitle = { attrs ->
        out << """<sitemesh:write property="title">${attrs.default?:''}</sitemesh:write>""".toString()
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

    /**
     * Renders a template inside views for collections, models and beans. Examples:<br/>
     *
     * &lt;g:render template="atemplate" collection="${users}" /&gt;<br/>
     * &lt;g:render template="atemplate" model="[user:user,company:company]" /&gt;<br/>
     * &lt;g:render template="atemplate" bean="${user}" /&gt;<br/>
     *
     * @attr template REQUIRED The name of the template to apply
     * @attr contextPath the context path to use (relative to the application context path). Defaults to "" or path to the plugin for a plugin view or template.
     * @attr bean The bean to apply the template against
     * @attr model The model to apply the template against as a java.util.Map
     * @attr collection A collection of model objects to apply the template to
     * @attr var The variable name of the bean to be referenced in the template
     * @attr plugin The plugin to look for the template in
     */
    Closure render = { Map attrs, body ->
        groovyPagesTemplateRenderer.render(getWebRequest(), getPageScope(), attrs, body, getOut())
        null
    }

    /**
     * Renders an exception for the errors view
     *
     * @attr exception REQUIRED The exception to render
     */
    Closure renderException = { Map attrs ->
        if (!(attrs?.exception instanceof Throwable)) {
            return
        }
        Throwable exception = (Throwable)attrs.exception

        Encoder htmlEncoder = codecLookup.lookupEncoder('HTML')

        def currentOut = out
        int statusCode = request.getAttribute('javax.servlet.error.status_code') as int
        currentOut << """<h1>Error ${prettyPrintStatus(statusCode)}</h1>
<dl class="error-details">
<dt>URI</dt><dd>${htmlEncoder.encode(WebUtils.getForwardURI(request) ?: request.getAttribute('javax.servlet.error.request_uri'))}</dd>
"""

        def root = ExceptionUtils.getRootCause(exception)
        currentOut << "<dt>Class</dt><dd>${root?.getClass()?.name ?: exception.getClass().name}</dd>"
        currentOut << "<dt>Message</dt><dd>${htmlEncoder.encode(exception.message)}</dd>"
        if (root != null && root != exception && root.message != exception.message) {
            currentOut << "<dt>Caused by</dt><dd>${htmlEncoder.encode(root.message)}</dd>"
        }
        currentOut << "</dl>"

        currentOut << errorsViewStackTracePrinter.prettyPrintCodeSnippet(exception)

        def trace = errorsViewStackTracePrinter.prettyPrint(exception.cause ?: exception)
        if (StringUtils.hasText(trace.trim())) {
            currentOut << "<h2>Trace</h2>"
            currentOut << '<pre class="stack">'
            currentOut << htmlEncoder.encode(trace)
            currentOut << '</pre>'
        }
    }

    private String prettyPrintStatus(int statusCode) {
        String httpStatusReason = HttpStatus.valueOf(statusCode).getReasonPhrase()
        "$statusCode: ${httpStatusReason}"
    }
}
