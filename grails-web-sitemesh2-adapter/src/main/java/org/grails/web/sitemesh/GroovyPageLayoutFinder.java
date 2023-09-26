package org.grails.web.sitemesh;

/*
This is needed for compilation of the plugin, but not needed for distribution.
Caused by: java.lang.NoClassDefFoundError: org/grails/web/sitemesh/GroovyPageLayoutFinder
        at org.grails.plugins.web.interceptors.InterceptorArtefactHandler.<clinit>(InterceptorArtefactHandler.groovy:33)
 grails-core:trait Controller -> trait ResponseRenderer (GroovyPageLayoutFinder)
 */
public class GroovyPageLayoutFinder {}
