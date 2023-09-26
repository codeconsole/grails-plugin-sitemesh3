package org.grails.web.sitemesh;

import grails.util.Environment;

public class GroovyPageLayoutFinder {
    public static final String LAYOUT_ATTRIBUTE = "org.grails.layout.name";
    public static final String NONE_LAYOUT = "_none_";
    public static final String RENDERING_VIEW_ATTRIBUTE = "org.grails.rendering.view";

    private boolean gspReloadEnabled;
    private boolean cacheEnabled = (Environment.getCurrent() != Environment.DEVELOPMENT);
    private boolean enableNonGspViews = false;
    private String defaultDecoratorName;

    public void setGspReloadEnabled(boolean gspReloadEnabled) {
        this.gspReloadEnabled = gspReloadEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public void setEnableNonGspViews(boolean enableNonGspViews) {
        this.enableNonGspViews = enableNonGspViews;
    }

    public void setDefaultDecoratorName(String defaultDecoratorName) {
        this.defaultDecoratorName = defaultDecoratorName;
    }
}
