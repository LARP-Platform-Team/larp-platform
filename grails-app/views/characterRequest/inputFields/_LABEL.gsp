<%@ page import="ru.srms.larp.platform.game.character.request.FormFieldValue" %>
<g:set var="fieldValue" value="${field as FormFieldValue}"/>

<div class="field">
  <div class="ui basic blsck label">
    ${fieldValue.field.title}
  </div>
</div>