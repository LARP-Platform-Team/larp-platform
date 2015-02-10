package errors

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class ErrorsController {

    def error403() {}
    def error500() {
        render view: '/error'
    }
}
