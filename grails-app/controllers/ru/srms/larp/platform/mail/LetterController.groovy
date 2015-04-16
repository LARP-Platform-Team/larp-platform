package ru.srms.larp.platform.mail

import grails.transaction.Transactional
import ru.srms.larp.platform.game.mail.LetterContent

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class LetterController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond LetterContent.list(params), model:[letterInstanceCount: LetterContent.count()]
    }

    def show(LetterContent letterInstance) {
        respond letterInstance
    }

    def create() {
        respond new LetterContent(params)
    }

    @Transactional
    def save(LetterContent letterInstance) {
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

    def edit(LetterContent letterInstance) {
        respond letterInstance
    }

    @Transactional
    def update(LetterContent letterInstance) {
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
    def delete(LetterContent letterInstance) {

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
