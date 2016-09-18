<%@ page import="ru.srms.larp.platform.game.character.request.FieldType" %>

<div class="${hasErrors(bean: subject, field: 'title', 'error')} required field">
  <label for="title">Название:</label>
  <g:textField name="title" required="" value="${subject?.title}"/>
  <div class="ui pointing label">Максимум 300 символов.</div>
</div>

<div class="${hasErrors(bean: subject, field: 'type', 'error')} required field">
  <label for="type">Тип:</label>
  <g:select name="type" id="requestFieldType" from="${FieldType.values()}" class="ui dropdown"
            required="" optionValue="title" value="${subject?.type}"/>
</div>

<div id="requestFieldDataWrapper" class="${hasErrors(bean: subject, field: 'data', 'error')}
  ${FieldType.SELECT.equals(subject?.type) ? "" : "larp-hidden"} field"
     id="requestFieldDataInput">
  <label for="data">Варианты ответа:</label>
  <g:textField name="data" value="${subject?.data}"/>
  <div class="ui pointing label">Введите варианты через ";". Максимум 512 символов.</div>
</div>

<div class="${hasErrors(bean: subject, field: 'hint', 'error')} field">
  <label for="hint">Подсказка:</label>
  <g:textField name="hint" value="${subject?.hint}"/>
  <div class="ui pointing label">Подсказка для поля, как вот эта. Максимум 300 символов.</div>
</div>

<div class="${hasErrors(bean: subject, field: 'required', 'error')} field">
  <label for="required">Обязательное:</label>
  <g:checkBox name="required" value="${subject?.required}"/>
</div>

<g:if test="${params.role?.id}">
  <g:hiddenField name="role.id" value="${params.role.id}"/>
</g:if>