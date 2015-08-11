<%@ page import="ru.srms.larp.platform.game.character.GameCharacter; ru.srms.larp.platform.game.mail.MailBox" %>

<div class="${hasErrors(bean: mailBoxInstance, field: 'address', 'error')} required field">
	<label for="address">Адрес</label>
	<g:field type="email" name="address" placeholder="address@domain.zon" required="" value="${mailBoxInstance?.address}"/>
</div>

<div class="${hasErrors(bean: mailBoxInstance, field: 'name', 'error')} field">
	<label for="name">Имя отправителя</label>
	<g:textField name="name" value="${mailBoxInstance?.name}"/>
</div>

<div class="${hasErrors(bean: mailBoxInstance, field: 'owner', 'error')} field">
	<label for="owner">Владелец</label>
	<g:select id="owner" name="owner.id" from="${GameCharacter.findAllByGame(params.game)}"
						optionKey="id" value="${mailBoxInstance?.owner?.id}" class="dropdown" noSelection="['null': 'нет']"/>
</div>