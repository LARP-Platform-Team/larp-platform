<%@ page import="ru.srms.larp.platform.game.character.request.FormFieldValue" %>
<g:set var="fieldValue" value="${field as FormFieldValue}"/>

<div class="${hasErrors(bean: fieldValue, field: 'value', 'error')} ${fieldValue.field.required ? 'required' : ''} field">
  <label for="request_from_field_${fieldValue.field.id}">${fieldValue.field.title}:</label>

  <g:if test="${!isHidden && fieldValue.field.required}">
    <g:textArea required="required" data-is-required="true" class="simple rich"
                name="request_from_field_${fieldValue.field.id}" value="${fieldValue.value}"/>
  </g:if>
  <g:else>
    <g:textArea class="simple rich" data-is-required="${fieldValue.field.required}"
        name="request_from_field_${fieldValue.field.id}" value="${fieldValue.value}"/>
  </g:else>

  <g:if test="${fieldValue.field.hint}">
    <div class="ui pointing label">${fieldValue.field.hint}</div>
  </g:if>
</div>