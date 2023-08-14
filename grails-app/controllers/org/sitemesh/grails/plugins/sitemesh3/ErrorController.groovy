package org.sitemesh.grails.plugins.sitemesh3

class ErrorController {

    def index() {
        def exception = request.exception?:request.getAttribute('javax.servlet.error.exception')
        Map model = [error:exception?.message]
        if (request.forwardURI?.endsWith('.json')) {
            params.format = 'json'
        }
        respond model, view:'/error'
    }
}