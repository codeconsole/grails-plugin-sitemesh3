package org.sitemesh.grails.plugins.sitemesh3.tagrules;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ContentBlockExtractingRule;
import org.sitemesh.tagprocessor.State;

public class GrailsTagRuleBundle implements TagRuleBundle {

    public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        defaultState.addRule("sitemesh:captureContent", new ContentBlockExtractingRule(contentProperty.getChild("page")));
        defaultState.addRule("content", new ContentBlockExtractingRule(contentProperty.getChild("page")));

        // merges id, style and class attributes on the body tag.
        defaultState.addRule("body", new ExportTagToContentAndMergeAttributesRule(siteMeshContext, contentProperty.getChild("body"), false));
    }

    public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        // No op.
    }
}
