<%@ page import="ru.srms.larp.platform.game.character.request.FormFieldValue" %>
<g:set var="fieldValue" value="${field as FormFieldValue}"/>
<g:set var="options" value="${fieldValue.field.data ? fieldValue.field.data.tokenize(';') : []}"/>

<div class="${hasErrors(bean: fieldValue, field: 'value', 'error')} ${fieldValue.field.required ? 'required' : ''} field">
  <label for="request_from_field_${fieldValue.field.id}">${fieldValue.field.title}:</label>

  <g:if test="${!isHidden && fieldValue.field.required}">
    <g:select required="required" data-is-required="true" class="ui dropdown" from="${options}"
                 name="request_from_field_${fieldValue.field.id}" value="${fieldValue.value}"/>
  </g:if>
  <g:else>
    <g:select class="ui dropdown" from="${options}" data-is-required="${fieldValue.field.required}"
                 name="request_from_field_${fieldValue.field.id}" value="${fieldValue.value}"/>
  </g:else>

  <g:if test="${fieldValue.field.hint}">
    <div class="ui pointing label">${fieldValue.field.hint}</div>
  </g:if>
</div>