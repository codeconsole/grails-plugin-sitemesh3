package org.sitemesh.grails.plugins.sitemesh3

import static org.springframework.http.HttpStatus.NOT_FOUND

class LayoutController {
    def get(String name) {
        if (!request.getAttribute('org.sitemesh.content.Content')) {
            render status: NOT_FOUND
        } else {
            render view: "/layouts/${name}"
        }
    }
}