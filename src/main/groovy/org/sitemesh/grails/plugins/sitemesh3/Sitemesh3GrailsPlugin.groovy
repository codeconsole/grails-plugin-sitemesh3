package org.sitemesh.grails.plugins.sitemesh3

import grails.plugins.*

import org.sitemesh.builder.SiteMeshFilterBuilder
import org.sitemesh.config.ConfigurableSiteMeshFilter
import org.sitemesh.grails.plugins.sitemesh3.config.GrailsMetaTagBasedDecoratorSelector
import org.sitemesh.grails.plugins.sitemesh3.tagrules.GrailsTagRuleBundle
import org.sitemesh.webapp.WebAppContext
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.filter.OrderedFilter
import org.springframework.core.Ordered

class Sitemesh3GrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
       def grailsVersion = "6.0.0  > *"
       // resources that are excluded from plugin packaging
       def pluginExcludes = [
           "grails-app/views/error.gsp"
       ]

       // TODO Fill in these fields
       def title = "Sitemesh3" // Headline display name of the plugin
       def author = "Your name"
       def authorEmail = ""
       def description = '''\
   Brief summary/description of the plugin.
   '''
       def profiles = ['web']
       // URL to the plugin's documentation
       def documentation = "https://github.com/codeconsole/grails-sitemesh3"

       // Extra (optional) plugin metadata

       // License: one of 'APACHE', 'GPL2', 'GPL3'
       def license = "APACHE"

       // Details of company behind the plugin (if there is one)
       def organization = [ name: "SiteMesh", url: "https://www.sitemesh.org" ]

       // Any additional developers beyond the author specified above.
       def developers = [ [ name: "Scott Murphy" ]]

       // Location of the plugin's issue tracker.
       def issueManagement = [ system: "GitHub", url: "https://github.com/codeconsole/grails-sitemesh3/issues" ]

       // Online location of the plugin's browseable source code.
       def scm = [ url: "https://github.com/codeconsole/grails-sitemesh3" ]

       def loadAfter = ['groovyPages']
       def influences = ['controllers']

       Closure doWithSpring() { {->
               sitemesh3Filter(FilterRegistrationBean) {
                   filter = new ConfigurableSiteMeshFilter() {
                       @Override
                       protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
                           builder.addTagRuleBundle(new GrailsTagRuleBundle())
                           builder.setCustomDecoratorSelector(new GrailsMetaTagBasedDecoratorSelector<WebAppContext>())
                       }
                   }
                   urlPatterns = ['/*']
                   // has to be before grailsWebRequestFilter
                   // https://github.com/grails/grails-core/blob/c56c55649f7b3df614bd603ee84756324a3f8df3/grails-plugin-controllers/src/main/groovy/org/grails/plugins/web/controllers/ControllersGrailsPlugin.groovy#L110
                   order = OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER + 29
               }
           }
       }

       void doWithDynamicMethods() {
           // TODO Implement registering dynamic methods to classes (optional)
       }

       void doWithApplicationContext() {
           // TODO Implement post initialization spring config (optional)
       }

       void onChange(Map<String, Object> event) {
           // TODO Implement code that is executed when any artefact that this plugin is
           // watching is modified and reloaded. The event contains: event.source,
           // event.application, event.manager, event.ctx, and event.plugin.
       }

       void onConfigChange(Map<String, Object> event) {
           // TODO Implement code that is executed when the project configuration changes.
           // The event is the same as for 'onChange'.
       }

       void onShutdown(Map<String, Object> event) {
           // TODO Implement code that is executed when the application shuts down (optional)
       }
}