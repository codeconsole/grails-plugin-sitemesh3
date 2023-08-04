# SiteMesh 3 Grails Plugin
SiteMesh 3 Grails Plugin demonstrating how to use [SiteMesh 3](https://github.com/sitemesh/sitemesh3) instead of [SiteMesh 2](https://github.com/sitemesh/sitemesh2)

*** Waiting on being included into Grails. [See progress here](https://github.com/grails/grails-core/issues/13058) ***

## NOTE - This version of the plugin requires the following merge:
https://github.com/grails/grails-gsp/pull/364
If you would like to use this version, feel free to build grails-gsp locally using that pull request.

## Please use the 1.x version of the plugin if you do not want to build anything:

If you wish to use SiteMesh 3 without that merge, you can use the [1.x version](https://github.com/codeconsole/grails-sitemesh3/tree/1.x) of this plugin

You can see a working example by running this plugin:
```./gradlew bootRun```

# Grails 6 Installation instructions:

### Step 1 - Install the plugin 

Modify `build.gradle` to use the plugin
```groovy
repositories {
    // ... existing repos here
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
}

dependencies {
    implementation("org.sitemesh:grails-plugin-sitemesh3:2.0-SNAPSHOT")
    implementation("org.grails.plugins:gsp:6.0.1-SNAPSHOT") // pull request grails-gsp#366 is needed
    // ... existing dependencies
}

configurations {
    all {
        // ... existing configurations
        exclude group:'opensymphony', module:'sitemesh'
        exclude group:'org.grails', module:'grails-web-gsp-taglib'
        exclude group:'org.grails', module:'grails-web-sitemesh'
    }
}
```
`oss snapshots` is needed temporarily until sitemesh 3.1.0 and the plugin are officially released.

### Step 2 -  You are done. Enjoy Bonus features
Your app is now using SiteMesh 3 and is no longer using SiteMesh 2. NO FURTHER CHANGES NEEDED.

**Bonus**: You can now enjoy multiple layouts on each page!


For instance, let's use `/grails-app/views/layouts/googleAnalyticsLayout.gsp` and `/grails-app/views/layouts/main.gsp`:

```html
<html lang="en">
<head>
    <meta name="layout" content="googleAnalyticsLayout, main"/>
    <title>Home</title>
</head>
```
