package ru.srms.larp.platform

import ru.srms.larp.platform.mail.Letter

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LetterController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Letter.list(params), model:[letterInstanceCount: Letter.count()]
    }

    def show(Letter letterInstance) {
        respond letterInstance
    }

    def create() {
        respond new Letter(params)
    }

    @Transactional
    def save(Letter letterInstance) {
        if (letterInstance == null) {
            notFound()
            return
        }

        if (letterInstance.hasErrors()) {
            respond letterInstance.errors, view:'create'
            return
        }

        letterInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'letter.label', default: 'Letter'), letterInstance.id])
                redirect letterInstance
            }
            '*' { respond letterInstance, [status: CREATED] }
        }
    }

    def edit(Letter letterInstance) {
        respond letterInstance
    }

    @Transactional
    def update(Letter letterInstance) {
        if (letterInstance == null) {
            notFound()
            return
        }

        if (letterInstance.hasErrors()) {
            respond letterInstance.errors, view:'edit'
            return
        }

        letterInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Letter.label', default: 'Letter'), letterInstance.id])
                redirect letterInstance
            }
            '*'{ respond letterInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Letter letterInstance) {

        if (letterInstance == null) {
            notFound()
            return
        }

        letterInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Letter.label', default: 'Letter'), letterInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'letter.label', default: 'Letter'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
