<%@ page import="org.springframework.validation.FieldError" %>
<g:hasErrors bean="${subject}">
  <ui:message type="error" title="Ошибка">
    <ul class="list">
      <g:eachError bean="${subject}" var="error">
        <li <g:if
                test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message
            error="${error}"/></li>
      </g:eachError>
    </ul>
  </ui:message>
</g:hasErrors>