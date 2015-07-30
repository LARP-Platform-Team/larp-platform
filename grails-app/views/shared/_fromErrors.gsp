<%@ page import="org.springframework.validation.FieldError" %>
<g:hasErrors bean="${item}">
  <ul class="errors" role="alert">
  <g:eachError bean="${item}" var="error">
    <li<g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>>
    <g:message error="${error}"/>
    </li>
  </g:eachError>
  </ul>
</g:hasErrors>