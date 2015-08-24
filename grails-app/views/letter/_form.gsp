<%@ page import="ru.srms.larp.platform.game.mail.LetterRef; ru.srms.larp.platform.game.mail.MailBox; ru.srms.larp.platform.game.mail.LetterContent" %>

<g:set var="letter" value="${letterRefInstance as LetterRef}"/>

<div class="ui two fields">
  <div class="${hasErrors(bean: letter, field: 'content.subject', 'error')} required field">
    <label for="content.subject">Тема</label>
    <g:textField name="content.subject" required="" value="${letter?.content?.subject}"/>
  </div>

  <div class="${hasErrors(bean: letter, field: 'content.recipients', 'error')} required field">
    <label for="content.recipients">Кому</label>
    <g:select name="content.recipients" from="${MailBox.findAllByIdNotEqual(params.mailboxId)}"
              value="${letter?.content?.recipients}" optionKey="id" multiple="true" class="ui dropdown"/>
  </div>
</div>

<div class="${hasErrors(bean: letter, field: 'content.text', 'error')} required field">
  <label for="content.text">Текст</label>
  <g:textArea name="content.text" required="" value="${letter?.content?.text}"/>
</div>

<g:hiddenField name="mailbox.id" value="${params.mailboxId ?: letter.mailbox.id}"/>