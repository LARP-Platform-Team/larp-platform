<%@ page import="ru.srms.larp.platform.game.roles.GameRole" %>

<div class="ui three fields">
  <div class="${hasErrors(bean: gameRoleInstance, field: 'title', 'error')} required field">
    <label for="title">Название</label>
    <g:textField name="title" required="" value="${gameRoleInstance?.title}"/>
    <div class="ui pointing label">Максимум 64 символа.</div>
  </div>

  <div class="${hasErrors(bean: subject, field: 'parent', 'error')} field">
    <label for="parent.id">Родительская роль</label>
    <g:select name="parent.id" value="${gameRoleInstance?.parent?.id}" class="dropdown"
              from="${GameRole.findAllByGame(params.game).findAll { it.id != gameRoleInstance.id }}"
              optionKey="id" optionValue="title" noSelection="${['null': 'нет']}"/>
  </div>
</div>