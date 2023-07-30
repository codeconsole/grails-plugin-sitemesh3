# SiteMesh 3 Grails Plugin

*** Waiting on being included into Grails. [See progress here](https://github.com/grails/grails-core/issues/13058) ***

SiteMesh 3 Grails Plugin demonstrating how to use [SiteMesh 3](https://github.com/sitemesh/sitemesh3) instead of [SiteMesh 2](https://github.com/sitemesh/sitemesh2)

You can see a working example by running this plugin:
```./gradlew bootRun```

# Grails 6 Installation instructions:

### Step 1 - Build the plugin 
```
git clone http://github.com/codeconsole/grails-sitemesh3
cd grails-sitemesh3
./gradlew publishToMavenLocal
```

### Step 2 - Install the plugin into an Existing Grails app
1. Disable SiteMesh 2 using existing grails configuration either in `application.yml`
```
grails:
  gsp:
    view:
      layoutViewResolver: false
```
or in `application.properties`
```properties
grails.gsp.view.layoutViewResolver=false
```

2. Modify `build.gradle` to use the plugin
```
repositories {
    mavenLocal() // added 
    ... // existing repos here
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" } // added
}

dependencies {
    implementation("org.sitemesh:sitemesh:3.1.0-SNAPSHOT")  // from snapshot repo
    implementation("org.sitemesh.grails.plugins.sitemesh3:grails-plugin-sitemesh3:0.1-SNAPSHOT") // from mavenLocal
    ...  // existing dependencies
```
Note: `mavenLocal` repo is needed to use the plugin you just built and `oss snapshots` is needed temporarily until sitemesh 3.1.0 is released.

### Step 3 -  You are done. Enjoy Bonus features
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
