package org.sitemesh.grails.plugins.sitemesh3

class UrlMappings {
    static mappings = {
        "/helloJsp"(view:"/hello")
        "/"(view:"/index")
        "/index(.$format)?"(view:"/index")
//        "500"(view:'/error')
        "500"(controller: 'error', action:'index')
        "404"(view:'/notFound')
    }
}
