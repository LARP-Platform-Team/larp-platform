package ru.srms.larp.platform

import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.NOT_FOUND

abstract class BaseController {

    /**
     * Computes maximum of entries per page for pagination
     * @param max max parameter coming from request
     * @return maximum of entries
     */
    protected int pageMax(Integer max) {
        Math.min(max ?: 10, 100)
    }

    /**
     * Composes map with pagination options based on request parameters.<br/>
     * Also sets up params.max value.
     * @return map with offset and max entries for pagination
     */
    protected Map paginator(Integer max = null) {
        params.max = pageMax(params.int('max') ?: max)
        [offset: params.offset ?: 0, max: params.max]
    }

    /**
     * Validates input data for domain object and make redirects if instance is not found or
     * contains errors
     * TODO find common base class for domain objects
     * @param object domain instance object
     * @param view view to redirect to
     * @return {@code true} if validation is successful, {@code false} otherwise
     */
    protected boolean validateData(def object, String view) {
        if (object == null) {
            notFound()
            return false
        }

        if (view && object.hasErrors()) {
            respond object.getErrors(), view: view
            return false
        }

        return true
    }

    /**
     * Validates input data for domain object and make redirects if instance is not found
     * @param object domain instance object
     * @return {@code true} if validation is successful, {@code false} otherwise
     */
    protected boolean validateData(def object) {
        return validateData(object, null)
    }

    /**
     * @return i18n message code for domain class label
     */
    abstract protected String labelCode()

    /**
     * Make appropriate respond if instance was not found
     */
    protected void notFound() {
        respondChange('default.not.found.message', NOT_FOUND, null, params.id)
    }

    /**
     * Make appropriate respond after instance was somehow changed
     * @param messageCode code for information message relevant to performed action
     * @param status http status code
     * @param object domain class instance
     * @param id domain class instance id
     * @return The result of the "request.withFormat" closure call
     */
    protected def respondChange(String messageCode, HttpStatus status, def object, def id = null) {

        flash.message = message(code: messageCode, args: [message(code: labelCode()), id ?: object?.id])
        redirect redirectParams()

        // TODO если "удалить" делать ссылкой, то редиректа не происходит. разобраться.
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: messageCode, args: [message(code: labelCode()), id ?: object?.id])
//                redirect action: 'index', params: [gameAlias: params.gameAlias], method: "GET"
//            }
//            '*' {
//                if (object)
//                    respond object, [status: status]
//                else
//                    render status: status
//            }
//        }
    }

    /**
     * May be overriden for custom redirect rules
     * @return map of redirect params
     */
    protected Map redirectParams() {
        def defaults = [action: 'index', params: [:], method: "GET"]
        if(params.gameAlias)
            defaults.params.gameAlias = params.gameAlias
        if(params.charAlias)
            defaults.params.charAlias = params.charAlias
        return defaults
    }
}
