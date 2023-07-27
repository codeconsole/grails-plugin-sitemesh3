# SiteMesh 3 Grails Plugin

SiteMesh 3 Grails Plugin demonstrating how to use [SiteMesh 3](https://github.com/sitemesh/sitemesh3) instead of [SiteMesh 2](https://github.com/sitemesh/sitemesh2)

You can see a working example by running:
```./gradlew bootRun```

The way the plugin works is by providing Grails layout tag replacements and disabling Site Mesh 2 using the `layoutViewResolver`
setting provided by the [GroovyPagesGrailsPlugin](https://github.com/grails/grails-gsp/blob/6.0.x/grails-plugin-gsp/src/main/groovy/org/grails/plugins/web/GroovyPagesGrailsPlugin.groovy).

either in `application.properties`
```properties
grails.gsp.view.layoutViewResolver=false
```
or in `application.yml`:
```yml
grails:
  gsp:
    view:
      layoutViewResolver: false
```

The only difference between SiteMesh 2 and SiteMesh 3 would then be layouts paths:

```<meta name="layout" content="main"/>```
needs to be converted to
```<meta name="decorator" content="/layouts/main"/>```
