# SiteMesh 3 Grails Plugin

SiteMesh 3 Grails Plugin demonstrating how to use [SiteMesh 3](https://github.com/sitemesh/sitemesh3) instead of [SiteMesh 2](https://github.com/sitemesh/sitemesh2)

You can see a working example by running:
```./gradlew bootRun```

The way the plugin works is by providing the necessary tag library replacement and disabling Site Mesh 2 using the `layoutViewResolver`
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
````
