package org.sitemesh.grails.plugins.sitemesh3.config;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.config.PathBasedDecoratorSelector;
import org.sitemesh.content.Content;

import java.io.IOException;

public class GrailsMetaTagBasedDecoratorSelector<C extends SiteMeshContext> extends PathBasedDecoratorSelector<C> {

    public GrailsMetaTagBasedDecoratorSelector put(String contentPath, String... decoratorPaths) {
        super.put(contentPath, decoratorPaths);
        return this;
    }

    public String[] selectDecoratorPaths(Content content, C siteMeshContext) throws IOException {
        String decorator = content.getExtractedProperties()
                .getChild("meta")
                .getChild("layout")
                .getValue();
        if (decorator != null) {
            String[] decorators = decorator.split(",");
            for (int i = 0; i < decorators.length; i++) {
                decorators[i] = "/layouts/" + decorators[i];
            }
            return decorators;
        }
        return super.selectDecoratorPaths(content, siteMeshContext);
    }
}
