package org.sitemesh.grails.plugins.sitemesh3;

import org.grails.web.gsp.io.GrailsConventionGroovyPageLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class GrailsLayoutHandlerMapping extends AbstractHandlerMapping {
    @Autowired
    GrailsConventionGroovyPageLocator groovyPageLocator;

    Map<String, ParameterizableViewController> layoutCache = new HashMap<>();

    public GrailsLayoutHandlerMapping() {
        setOrder(-6);
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.startsWith("/layouts")) {
            ParameterizableViewController pvc = layoutCache.get(uri);
            if (pvc == null) {
                if (groovyPageLocator.findViewByPath(uri) == null) {
                    throw new ResponseStatusException(NOT_FOUND, "Unable to find resource "+uri);
                }
                pvc = new ParameterizableViewController();
                pvc.setSupportedMethods(HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.POST.name());
                pvc.setViewName(uri);
                layoutCache.put(uri, pvc);
            }
            return pvc;
        }
        return null;
    }
}