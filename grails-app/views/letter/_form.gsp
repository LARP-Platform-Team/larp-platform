<%@ page import="ru.srms.larp.platform.game.mail.LetterRef; ru.srms.larp.platform.game.mail.MailBox; ru.srms.larp.platform.game.mail.LetterContent" %>

<g:set var="letter" value="${letterRefInstance as LetterRef}"/>

<div class="fieldcontain ${hasErrors(bean: letter, field: 'content.recipients', 'error')} required">
	<label for="content.recipients">Кому<span class="required-indicator">*</span></label>
	<g:select name="content.recipients" from="${MailBox.findAllByIdNotEqual(params.mailboxId)}"
		value="${letter?.content?.recipients}" optionKey="id" multiple="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letter, field: 'content.subject', 'error')} required">
	<label for="content.subject">Тема<span class="required-indicator">*</span></label>
	<g:textField name="content.subject" required="" value="${letter?.content?.subject}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letter, field: 'content.text', 'error')} required">
	<label for="content.text">Текст<span class="required-indicator">*</span></label>
	<g:textArea name="content.text" required="" value="${letter?.content?.text}"/>
</div>

<g:hiddenField name="mailbox.id" value="${params.mailboxId ?: letter.mailbox.id}"/>