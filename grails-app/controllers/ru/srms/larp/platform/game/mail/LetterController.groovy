package ru.srms.larp.platform.game.mail

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import ru.srms.larp.platform.BaseModuleController
import ru.srms.larp.platform.MailboxService
import ru.srms.larp.platform.game.Game

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT

@Secured(['IS_AUTHENTICATED_FULLY'])
@Transactional(readOnly = true)
class LetterController extends BaseModuleController {

  MailboxService mailboxService
  static allowedMethods = [save: "POST", update: "POST"]

  def show(LetterRef letter) {
    withModule {
      respond mailboxService.showLetter(letter)
    }
  }

  def compose() {
    withModule {
      def box = MailBox.get(params.mailboxId)
      if (!box || box.game != params.game)
        throw new Exception("Неверный ящик")
      respond mailboxService.writeLetter(box)
    }
  }

  def edit(LetterRef letter) {
    withModule {
      if (letter.type != LetterType.DRAFT)
        throw new Exception("Можно редактировать только черновики")

      if (letter.mailbox.game != params.game)
        throw new Exception("Неверный ящик")
      respond mailboxService.editLetter(letter)
    }
  }

  @Transactional
  def save() {
    withModule {
      def letter = new LetterRef()
      letter.properties = params
      def content = letter.content

      letter.type = params.containsKey('send') ? LetterType.OUTGOING : LetterType.DRAFT
      content.sender = letter.mailbox
      content.text = cleanHtml(content.text, 'rich-text')
      content.time = new Date()

      if (validateData(letter, 'compose')) {
        mailboxService.saveLetter(letter)
        respondChange('Письмо сохранено', CREATED, letter)
      }
    }
  }

  @Transactional
  def update(LetterRef letter) {
    withModule {
      if (letter.type != LetterType.DRAFT)
        throw new Exception("Можно отправить только из черновиков")

      letter.type = params.containsKey('send') ? LetterType.OUTGOING : LetterType.DRAFT
      letter.content.text = cleanHtml(letter.content.text, 'rich-text')
      if (validateData(letter, 'edit')) {
        mailboxService.saveLetter(letter)
        respondChange('Письмо обновлено', CREATED, letter)
      }
    }
  }

  @Transactional
  def send(LetterRef letter) {
    withModule {
      if (letter.type != LetterType.DRAFT)
        throw new Exception("Можно отправить только из черновиков")

      letter.type = LetterType.OUTGOING
      letter.content.text = cleanHtml(letter.content.text, 'rich-text')
      if (validateData(letter, letter.id ? 'edit': 'compose')) {
        mailboxService.saveLetter(letter)
        respondChange('Письмо успешно отправлено', CREATED, letter)
      }
    }
  }

  @Transactional
  def delete(LetterRef letter) {
    withModule {
      if (validateData(letter)) {
        mailboxService.deleteLetter(letter)
        respondChange('Письмо удалено', NO_CONTENT, null, letter.id)
      }
    }
  }

  @Override
  protected Map redirectParams() {
    def result = super.redirectParams()
    result.action = 'show'
    result.controller = 'MailBox'
    result.params.id = params?.mailbox?.id ?: LetterRef.get(params.id).mailbox.id
    return result
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.MAIL
  }
}
