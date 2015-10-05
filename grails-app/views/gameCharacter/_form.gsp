<%@ page import="ru.srms.larp.platform.sec.SpringUser; ru.srms.larp.platform.game.Game" %>

  <div class="${hasErrors(bean: gameCharacterInstance, field: 'name', 'error')} required field">
    <label for="name">Имя</label>
    <g:textField name="name" required="" placeholder="Иван" value="${gameCharacterInstance?.name}"/>
    <div class="ui pointing label">Максимум 64 символа. Должно быть уникальным в рамках игры.</div>
</div>

  <div class="${hasErrors(bean: gameCharacterInstance, field: 'alias', 'error')} required field">
    <label for="name">Алиас</label>
    <g:textField name="alias" required="" placeholder="ivan"
                 value="${gameCharacterInstance?.alias}"/>
    <div class="ui pointing label">Так имя персонажа будет отображаться в ссылке.
    Максимум 32 символа, можно использовать латинские буквы, цифры и тире. Должен быть уникальным в рамках игры.</div>
</div>

  <div class="${hasErrors(bean: gameCharacterInstance, field: 'player', 'error')} field">
    <label for="name">Игрок</label>
    <g:select name="player" class="ui dropdown" from="${SpringUser.list()}"
              optionKey="id" optionValue="name" noSelection="${['null': '']}"
              value="${gameCharacterInstance?.player?.id}"/>
    <div class="ui pointing label">Игрок, управляющий персонажем. Оставьте пустым,
  и тогда это будет NPC - мастерский персонаж.</div>
</div>