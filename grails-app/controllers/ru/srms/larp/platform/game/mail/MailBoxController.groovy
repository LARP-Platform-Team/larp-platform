package ru.srms.larp.platform.game.mail

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.MailboxService
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.*

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class MailBoxController extends BaseModuleController {

  static allowedMethods = [save: "POST", update: "POST"]
  MailboxService mailboxService

  def index() {
    withModule {
      respond mailboxService.all(params.game, paginator()),
          model: [itemsCount: mailboxService.countAll(params.game)]
    }
  }

  // TODO ---
  def show(MailBox instance) {
    withModule {
      respond instance, model: [
          letters: mailboxService.letters(instance)]
    }
  }

  def create() {
    withModule {
      respond mailboxService.create(params.game)
    }
  }

  def edit(MailBox instance) {
    withModule {
      respond mailboxService.edit(instance)
    }
  }

  @Transactional
  def save(MailBox instance) {
    withModule {
      if (validateData(instance, 'create')) {
        mailboxService.save(instance)
        respondChange('Почтовый ящик успешно создан', CREATED, instance)
      }
    }
  }

  @Transactional
  def update() {
    withModule {
      def instance = MailBox.get(params.id)
      def oldOwner = instance.owner
      instance.properties = params
      if (validateData(instance, 'edit')) {
        mailboxService.save(instance, oldOwner)
        respondChange('Почтовый ящик обновлен', OK, instance)
      }
    }
  }

  @Transactional
  def delete(MailBox instance) {
    withModule {
      if (validateData(instance)) {
        mailboxService.delete(instance)
        respondChange('Почтовый ящик удален', NO_CONTENT, null, instance.id)
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.MAIL
  }
}