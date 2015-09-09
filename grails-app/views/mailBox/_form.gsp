<%@ page import="ru.srms.larp.platform.game.character.GameCharacter; ru.srms.larp.platform.game.mail.MailBox" %>

<div class="${hasErrors(bean: mailBoxInstance, field: 'address', 'error')} required field">
  <label for="address">Адрес</label>
  <g:field type="email" name="address" placeholder="address@domain.zon" required=""
           value="${mailBoxInstance?.address}"/>
  <div class="ui pointing label">Максимум 64 символа. Адрес должен иметь формат E-mail и быть уникальным в рамках игры.</div>
</div>

<div class="${hasErrors(bean: mailBoxInstance, field: 'name', 'error')} field">
  <label for="name">Имя отправителя</label>
  <g:textField name="name" value="${mailBoxInstance?.name}"/>
  <div class="ui pointing label">Имя отправителя, связанное с ящиком. Можно оставить пустым.</div>
</div>

<div class="${hasErrors(bean: mailBoxInstance, field: 'owner', 'error')} field">
  <label for="owner">Владелец</label>
  <g:select id="owner" name="owner.id" from="${GameCharacter.findAllByGame(params.game)}"
            optionKey="id" value="${mailBoxInstance?.owner?.id}" class="dropdown"
            noSelection="['null': 'нет']"/>
  <div class="ui pointing label">Игрок, управляющий ящиком. Оставьте пустым, чтобы сделать "мастерский" ящик.</div>
</div>