# SiteMesh 3 Grails Plugin
SiteMesh 3 Grails Plugin demonstrating how to use [SiteMesh 3](https://github.com/sitemesh/sitemesh3) instead of [SiteMesh 2](https://github.com/sitemesh/sitemesh2)

 [See discussion here](https://github.com/grails/grails-core/issues/13058)

## Want to See the Full Power for Sitemesh 3?
Check out using SiteMesh 3 layouts on GSP and JSP pages, error pages, specifying layouts in controllers, and even applying multiple layouts to the same view!

You can see a working example by running this plugin:
```./gradlew bootRun```


# Grails 6 Installation instructions:

### Step 1 - Install the plugin 

Modify `build.gradle` to use the plugin
```groovy
repositories {
    // ...
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
}

dependencies {
    implementation("org.sitemesh:grails-plugin-sitemesh3:7.0.0-SNAPSHOT")
    // ... existing dependencies
}
```

`oss snapshots` is needed until the plugin are officially released.

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
