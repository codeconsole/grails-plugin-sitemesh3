package org.sitemesh.grails.plugins.sitemesh3

import grails.core.gsp.GrailsTagLibClass
import grails.plugins.*
import org.grails.config.PropertySourcesConfig
import org.grails.core.artefact.TagLibArtefactHandler
import org.grails.taglib.TagLibraryLookup
import org.grails.taglib.TagLibraryMetaUtils
import org.springframework.core.env.MapPropertySource

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

    Closure doWithSpring() {
        { ->
            def propertySources = application.mainContext.environment.getPropertySources()
            propertySources.addFirst(new MapPropertySource("sitemesh3Properties", [
                    'grails.gsp.view.layoutViewResolver':'false',
                    'spring.sitemesh.decorator.metaTag': 'layout',
                    'spring.sitemesh.decorator.attribute': 'org.grails.layout.name',
                    'spring.sitemesh.decorator.prefix': '/layouts/',
                    'spring.sitemesh.decorator.bundles': ['sm2'],
            ]))
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