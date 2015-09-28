<%@ page import="ru.srms.larp.platform.game.character.request.FormFieldValue" %>
<g:set var="fieldValue" value="${field as FormFieldValue}"/>
<g:set var="checkboxValue" value="${fieldValue.value ? Boolean.valueOf(fieldValue.value) : false}"/>

<div class="${hasErrors(bean: fieldValue, field: 'value', 'error')} ${fieldValue.field.required ? 'required' : ''} field">
  <label for="request_from_field_${fieldValue.field.id}">${fieldValue.field.title}:</label>

  <g:if test="${fieldValue.field.required}">
    <g:checkBox required="required"
                 name="request_from_field_${fieldValue.field.id}" value="${checkboxValue}"/>
  </g:if>
  <g:else>
    <g:checkBox name="request_from_field_${fieldValue.field.id}" value="${checkboxValue}"/>
  </g:else>

  <g:if test="${fieldValue.field.hint}">
    <div class="ui pointing label">${fieldValue.field.hint}</div>
  </g:if>
</div>