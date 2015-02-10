<%@ page import="ru.srms.larp.platform.game.mail.Letter" %>



<div class="fieldcontain ${hasErrors(bean: letterInstance, field: 'subject', 'error')} required">
	<label for="subject">
		<g:message code="letter.subject.label" default="Subject" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="subject" required="" value="${letterInstance?.subject}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: letterInstance, field: 'text', 'error')} required">
	<label for="text">
		<g:message code="letter.text.label" default="Text" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="text" required="" value="${letterInstance?.text}"/>

</div>

