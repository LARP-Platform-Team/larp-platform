package ru.srms.larp.platform

import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.NOT_FOUND

abstract class BaseController {

    protected void notFound() {
        respondChange('default.not.found.message', NOT_FOUND, null, params.id)
    }

    protected def respondChange(String messageCode, String labelCode, HttpStatus respondStatus, def subject,
                                def id = null) {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: messageCode, args: [message(code: labelCode), id ?: subject?.id])
                redirect action: 'index', params: [gameAlias: params.gameAlias], method: "GET"
            }
            '*' {
                if (subject)
                    respond subject, [status: respondStatus]
                else
                    render status: respondStatus
            }
        }
    }
}
