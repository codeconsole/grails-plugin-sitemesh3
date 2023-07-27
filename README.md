# SiteMesh 3 Grails Plugin

Sample plugin demonstrating how to convert a Grails App to use Sitmesh 3 instead of Sitemesh 2

You can see a working example by running:
```./gradlew bootRun```

After adding the plugin, you could see what changes would be needed in an existing app [here](https://github.com/codeconsole/grails-sitemesh3/commit/5ac65f482f22c0df983c46813c5958f036c98fab)

If SiteMesh 2 can be elminitated from Grails, the `<sitemesh:` tag lib can be deleted and the `<g3:` taglib could be renamed to `<g:` and everything would be the same with the exception of how layouts are defined.

The only difference between SiteMesh 2 and SiteMesh 3 would then be layouts paths:
```<meta name="layout" content="main"/>```
is converted to 
```<meta name="decorator" content="/layouts/main"/>```

but this could be fixed internally after SiteMesh 2 is removed from Grais as well.
