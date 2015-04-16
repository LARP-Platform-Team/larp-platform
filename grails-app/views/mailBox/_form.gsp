<%@ page import="ru.srms.larp.platform.game.character.GameCharacter; ru.srms.larp.platform.game.mail.MailBox" %>

<div class="fieldcontain ${hasErrors(bean: mailBoxInstance, field: 'address', 'error')} required">
	<label for="address">Адрес<span class="required-indicator">*</span></label>
	<g:field type="email" name="address" required="" value="${mailBoxInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: mailBoxInstance, field: 'name', 'error')}">
	<label for="name">Имя отправителя</label>
	<g:textField name="name" value="${mailBoxInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: mailBoxInstance, field: 'owner', 'error')} ">
	<label for="owner">Владелец</label>
	<g:select id="owner" name="owner.id" from="${GameCharacter.findAllByGame(params.game)}"
						optionKey="id" value="${mailBoxInstance?.owner?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<g:hiddenField name="game.id" id="game" value="${params.game.id}"/>