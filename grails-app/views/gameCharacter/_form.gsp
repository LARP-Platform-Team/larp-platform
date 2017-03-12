<%@ page import="ru.srms.larp.platform.sec.SpringUser; ru.srms.larp.platform.game.Game" %>
<div class="${isCreate ? 'fields' : ''}">
  <div class="${hasErrors(bean: gameCharacterInstance, field: 'name', 'error')} required field">
    <label for="name">Имя</label>
    <g:textField name="name" required="" placeholder="Иван" value="${gameCharacterInstance?.name}"/>
    <div class="ui pointing label">Максимум 64 символа.</div>
  </div>

  <div class="${hasErrors(bean: gameCharacterInstance, field: 'alias', 'error')} required field">
    <label for="alias">Алиас</label>
    <g:textField name="alias" required="" placeholder="ivan"
                 value="${gameCharacterInstance?.alias}"/>
    <div class="ui pointing label">Так имя персонажа будет отображаться в ссылке.
    Максимум 32 символа, можно использовать латинские буквы, цифры и тире. Должен быть уникальным в рамках игры.</div>
  </div>

  <div class="${hasErrors(bean: gameCharacterInstance, field: 'player', 'error')} field">
    <label for="player">Игрок</label>
    <g:select name="player" class="ui dropdown" from="${SpringUser.list()}"
              optionKey="id" optionValue="name" noSelection="${['null': '']}"
              value="${gameCharacterInstance?.player?.id}"/>
    <div class="ui pointing label">Игрок, управляющий персонажем. Оставьте пустым,
    и тогда это будет NPC - мастерский персонаж.</div>
  </div>
</div>

<div class="fields">
  <div class="${hasErrors(bean: gameCharacterInstance, field: 'gmNote', 'error')} field">
    <label for="gmNote">Мастерские заметки</label>
    <g:textArea name="gmNote" class="simple rich" value="${gameCharacterInstance?.gmNote}"/>
    <div class="ui pointing label">Мастерские заметки о персонаже или игроке (до 1000 символов).</div>
  </div>
</div>