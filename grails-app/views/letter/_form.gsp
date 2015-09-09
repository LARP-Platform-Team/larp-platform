<%@ page import="ru.srms.larp.platform.game.mail.LetterRef; ru.srms.larp.platform.game.mail.MailBox; ru.srms.larp.platform.game.mail.LetterContent" %>

<g:set var="letter" value="${letterRefInstance as LetterRef}"/>

<div class="ui two fields">
  <div class="${hasErrors(bean: letter, field: 'content.subject', 'error')} required field">
    <label for="content.subject">Тема</label>
    <g:textField name="content.subject" required="" value="${letter?.content?.subject}"/>
    <div class="ui pointing label">Максимум 64 символа.</div>
  </div>

  <div class="${hasErrors(bean: letter, field: 'content.targetAddresses', 'error')} required field">
    <label for="content.targetAddresses">Кому</label>
    <g:textField name="content.targetAddresses" value="${letter?.content?.targetAddresses ?: letter?.content?.recipients?.collect { it.address } ?.join(', ')}"/>
    <div class="ui pointing label">Вводите адреса через запятую.</div>
  </div>
</div>

<div class="${hasErrors(bean: letter, field: 'content.text', 'error')} required field">
  <label for="content.text">Текст</label>
  <g:textArea name="content.text" required="" class="rich" value="${letter?.content?.text}"/>
</div>

<g:hiddenField name="mailbox.id" value="${params.mailboxId ?: letter.mailbox.id}"/>