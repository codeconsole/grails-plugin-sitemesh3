package org.sitemesh.grails.plugins.sitemesh3

import grails.plugins.Plugin
import org.grails.config.PropertySourcesConfig
import org.grails.web.gsp.io.GrailsConventionGroovyPageLocator
import org.grails.web.util.WebUtils
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.PropertySource

class Sitemesh3GrailsPlugin extends Plugin {

    def grailsVersion = "6.0.0  > *"
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = "SiteMesh 3"
    def author = "Scott Murphy"
    def authorEmail = ""
    def description = "Configures Grails to use SiteMesh 3 instead of SiteMesh 2"
    def profiles = ['web']
    def documentation = "https://github.com/codeconsole/grails-sitemesh3"

    def license = "APACHE"
    def organization = [name: "SiteMesh", url: "https://github.com/sitemesh"]
    def developers = [[name: "Scott Murphy"]]
    def issueManagement = [system: "GitHub", url: "https://github.com/codeconsole/grails-sitemesh3/issues"]
    def scm = [url: "https://github.com/codeconsole/grails-sitemesh3"]

    def loadBefore = ['groovyPages']

    static PropertySource getDefaultPropertySource(ConfigurableEnvironment configurableEnvironment, String defaultLayout) {

        Map props = [
                'grails.gsp.view.layoutViewResolver': 'false',
                'sitemesh.decorator.metaTag': 'layout',
                'sitemesh.decorator.attribute': WebUtils.LAYOUT_ATTRIBUTE,
                'sitemesh.decorator.prefix': '/layouts/',
                'sitemesh.decorator.tagRuleBundles': ['org.sitemesh.content.tagrules.html.Sm2TagRuleBundle'],
                'sitemesh.filter.order': SecurityProperties.BASIC_AUTH_ORDER - 9
        ]
        if (defaultLayout) {
            props['sitemesh.decorator.default'] = defaultLayout
        }
        // if property already exists, don't override
        props.clone().each {
            if (configurableEnvironment.getProperty(it.key)) {
                props.remove(it.key)
            }
        }
        return new MapPropertySource("sitemesh3Properties", props)
    }

    GrailsConventionGroovyPageLocator groovyPageLocator

    Closure doWithSpring() {
        { ->
            ConfigurableEnvironment configurableEnvironment = application.mainContext.environment
            def propertySources = configurableEnvironment.getPropertySources()
            // https://gsp.grails.org/latest/guide/layouts.html
            // Default view should be application, but it is inefficient to add a rule for a page that may not exist.
            propertySources.addFirst(getDefaultPropertySource(configurableEnvironment, grailsApplication.getConfig().get("grails.sitemesh.default.layout", null)))
            application.config = new PropertySourcesConfig(propertySources)
            grailsLayoutHandlerMapping(GrailsLayoutHandlerMapping)
        }
    }

    void doWithApplicationContext() {}

    void doWithDynamicMethods() {}

    void onChange(Map<String, Object> event) {}

    void onConfigChange(Map<String, Object> event) {}

    void onShutdown(Map<String, Object> event) {}
}