package org.grails.plugins.web.taglib

import org.grails.encoder.CodecLookup
import org.grails.encoder.Encoder
import org.grails.exceptions.ExceptionUtils
import org.grails.web.errors.ErrorsViewStackTracePrinter
import org.grails.web.gsp.GroovyPagesTemplateRenderer
import org.grails.web.util.WebUtils
import org.sitemesh.content.Content
import org.sitemesh.content.ContentProperty
import org.sitemesh.webapp.WebAppContext
import org.springframework.http.HttpStatus
import org.springframework.util.StringUtils

class RenderTagLib {

    CodecLookup codecLookup
    GroovyPagesTemplateRenderer groovyPagesTemplateRenderer
    ErrorsViewStackTracePrinter errorsViewStackTracePrinter

    Closure applyLayout = { Map attrs, body -> }

    private ContentProperty getContentProperty(String name) {
        if (!name) {
            return null
        }
        Content content = request.getAttribute(WebAppContext.CONTENT_KEY)
        ContentProperty currentProperty = content.getExtractedProperties();
        for (String childPropertyName : name.split("\\.")) {
            currentProperty = currentProperty.getChild(childPropertyName);
        }
        currentProperty
    }

    /**
     * Used to retrieve a property of the decorated page.<br/>
     *
     * &lt;g:pageProperty default="defaultValue" name="body.onload" /&gt;<br/>
     *
     * @emptyTag
     *
     * @attr REQUIRED name the property name
     * @attr default the default value to use if the property is null
     * @attr writeEntireProperty if true, writes the property in the form 'foo = "bar"', otherwise renders 'bar'
     */
    Closure pageProperty = { attrs ->
        if (!attrs.name) {
            throwTagError("Tag [pageProperty] is missing required attribute [name]")
        }
        String propertyName = attrs.name as String
        ContentProperty contentProperty = getContentProperty(propertyName)
        def propertyValue = contentProperty?.hasValue()? contentProperty.getValue() : attrs.'default' ?: null

        if (propertyValue) {
            if (attrs.writeEntireProperty) {
                out << ' '
                out << propertyName.substring(propertyName.lastIndexOf('.') + 1)
                out << "=\""
                out << propertyValue
                out << "\""
            } else {
                out << propertyValue
            }
        }
    }

    /**
     * Invokes the body of this tag if the page property exists:<br/>
     *
     * &lt;g:ifPageProperty name="meta.index"&gt;body to invoke&lt;/g:ifPageProperty&gt;<br/>
     *
     * or it equals a certain value:<br/>
     *
     * &lt;g:ifPageProperty name="meta.index" equals="blah"&gt;body to invoke&lt;/g:ifPageProperty&gt;
     *
     * @attr name REQUIRED the property name
     * @attr equals optional value to test against
     */
    Closure ifPageProperty = { Map attrs, body ->
        if (!attrs.name) {
            return
        }
        List names = ((attrs.name instanceof List) ? (List)attrs.name : [attrs.name])

        def invokeBody = true
        for (i in 0..<names.size()) {
            def propertyValue = getContentProperty(names[i] as String)?.getValue()
            if (propertyValue) {
                if (attrs.containsKey('equals')) {
                    if (attrs.equals instanceof List) {
                        invokeBody = ((List)attrs.equals)[i] == propertyValue
                    } else {
                        invokeBody = attrs.equals == propertyValue
                    }
                }
            } else {
                invokeBody = false
                break
            }
        }
        if (invokeBody && body instanceof Closure) {
            out << body()
        }
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
