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

      def replyLetter = null
      if(params.reply?.id) {
        def letterRef = LetterRef.get(params.reply?.id)
        if(letterRef.mailbox == box)
          replyLetter = letterRef
      }
      respond mailboxService.writeLetter(box, replyLetter)
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

  private def setupLetterContent(LetterRef letter, updateRecipients = false) {
    def content = letter.content
    content.sender = letter.mailbox
    content.text = cleanHtml(content.text, 'rich-text')
    content.time = new Date()
    content.updateRecipients = updateRecipients
  }

  @Transactional
  def save() {
    withModule {
      def letter = new LetterRef()
      letter.properties = params
      letter.type = params.containsKey('send') ? LetterType.OUTGOING : LetterType.DRAFT
      setupLetterContent(letter, true)

      if (validateData(letter, 'compose')) {
        mailboxService.saveLetter(letter)
        respondChange('Письмо сохранено', CREATED,
            [controller: 'MailBox', action: 'show', id: letter.mailbox.id])
      }
    }
  }

  @Transactional
  def update(LetterRef letter) {
    withModule {
      if (letter.type != LetterType.DRAFT)
        throw new Exception("Можно редактировать только из черновиков")

      letter.type = params.containsKey('send') ? LetterType.OUTGOING : LetterType.DRAFT
      setupLetterContent(letter, true)
      if (validateData(letter, 'edit')) {
        mailboxService.saveLetter(letter)
        respondChange('Письмо обновлено', CREATED,
            [controller: 'MailBox', action: 'show', id: letter.mailbox.id])
      }
    }
  }

  @Transactional
  def send(LetterRef letter) {
    withModule {
      if (letter.type != LetterType.DRAFT)
        throw new Exception("Можно отправить только из черновиков")

      letter.type = LetterType.OUTGOING
      setupLetterContent(letter)
      if (validateData(letter, letter.id ? 'edit': 'compose')) {
        mailboxService.saveLetter(letter)
        respondChange('Письмо успешно отправлено', CREATED,
            [controller: 'MailBox', action: 'show', id: letter.mailbox.id])
      }
    }
  }

  @Transactional
  def delete(LetterRef letter) {
    withModule {
      if (validateData(letter)) {
        mailboxService.deleteLetter(letter)
        respondChange('Письмо удалено', NO_CONTENT,
            [controller: 'MailBox', action: 'show', id: letter.mailbox.id])
      }
    }
  }

  @Override
  protected Game.GameModule module() {
    return Game.GameModule.MAIL
  }
}
