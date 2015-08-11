<%@ page import="ru.srms.larp.platform.game.character.GameCharacter; ru.srms.larp.platform.game.mail.MailBox" %>

<div class="${hasErrors(bean: gameResourceInstance, field: 'title', 'error')} required field">
    <label for="title">Название<span class="required-indicator">*</span></label>
    <g:field type="text" name="title" required="" value="${gameResourceInstance?.title}"/>
</div>

<div class="${hasErrors(bean: subject, field: 'identifierTitle', 'error')} field">
    <label for="identifierTitle">Название идентификатора</label>
    <g:field type="text" name="identifierTitle" required="" value="${gameResourceInstance?.identifierTitle}"/>
</div>

<div class="${hasErrors(bean: gameResourceInstance, field: 'measure', 'error')} field">
    <label for="measure">Единица измерения</label>
    <g:textField name="measure" value="${gameResourceInstance?.measure}"/>
</div>

<g:hiddenField name="game.id" id="game" value="${params.game.id}"/>