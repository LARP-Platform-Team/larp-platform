<%@ page import="ru.srms.larp.platform.game.character.GameCharacter" %>

<div class="fields">
  <div class="${hasErrors(bean: subject, field: 'code', 'error')} required five wide field">
    <label for="code">Код:</label>
    <g:textField name="code" required="" value="${subject?.code}"/>
    <div class="ui pointing label">Максимум 32 символа. Должен быть уникальным в рамках игры.</div>
  </div>

  <div
      class="${hasErrors(bean: subject, field: 'title', 'error')} five wide required field">
    <label for="title">Название:</label>
    <g:textField name="title" required="" value="${subject?.title}"/>
    <div
        class="ui pointing label">Максимум 128 символов. Должно быть уникальным в рамках игры.</div>
  </div>

  <div
      class="${hasErrors(bean: subject, field: 'owner', 'error')} five wide field">
    <label for="owner">Владелец:</label>
    <g:select name="owner" from="${GameCharacter.findAllByGame(params.game)}" optionKey="id"
              class="ui dropdown" value="${subject?.owner?.id}" noSelection="[null: '']"/>
  </div>
</div>

<div class="${hasErrors(bean: subject, field: 'notes', 'error')} field">
  <label for="notes">Заметки:</label>
  <g:textArea name="notes" class="simple rich" value="${subject?.notes}"/>
</div>