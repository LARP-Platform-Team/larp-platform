package ru.srms.larp.platform.game.mail

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseController
import ru.srms.larp.platform.MailboxService

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class MailBoxController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST"]
    MailboxService mailboxService

    def index() {
        respond mailboxService.all(params.game, paginator()) ,
            model: [itemsCount: mailboxService.countAll(params.game)]
    }

    // TODO ---
    def show(MailBox instance) {
        respond instance, model: [
            letters: mailboxService.letters(instance)]
    }

    def create() {
        respond mailboxService.create(params.game)
    }

    def edit(MailBox instance) {
        respond mailboxService.edit(instance)
    }

    @Transactional
    def save(MailBox instance) {
        if(validateData(instance, 'create')) {
            mailboxService.save(instance)
            respondChange('Почтовый ящик успешно создан', CREATED, instance)
        }
    }

    @Transactional
    def update() {

        def instance = MailBox.get(params.id)
        def oldOwner = instance.owner
        instance.properties = params
        if(validateData(instance, 'edit')) {
            mailboxService.save(instance, oldOwner)
            respondChange('Почтовый ящик обновлен', OK, instance)
        }
    }

    @Transactional
    def delete(MailBox instance) {
        if(validateData(instance)) {
            mailboxService.delete(instance)
            respondChange('Почтовый ящик удален', NO_CONTENT, null, instance.id)
        }
    }


}
