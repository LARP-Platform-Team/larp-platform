<%@ page import="ru.srms.larp.platform.game.roles.GameRole" %>
<g:set var="subject" value="${subject as GameRole}"/>

<div class="ui two fields">
  <div class="${hasErrors(bean: subject, field: 'title', 'error')} required field">
    <label for="title">Название</label>
    <g:textField name="title" required="" value="${subject?.title}"/>
    <div class="ui pointing label">Максимум 64 символа.</div>
  </div>
  
  <div class="${hasErrors(bean: subject, field: 'parent', 'error')} field">
    <label for="parent.id">Родительская роль</label>
    <g:select name="parent.id" value="${subject?.parent?.id}" class="dropdown"
              from="${subject.getAvailableParents()}"
              optionKey="id" optionValue="title" data-placeholder="Выберите роль" noSelection="${['null': '']}"/>
  </div>
</div>