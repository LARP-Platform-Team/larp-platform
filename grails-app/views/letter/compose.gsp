<%@ page import="org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="Написать письмо"/>
  <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </li>
  </ul>
</div>

<div id="create-letter" class="content scaffold-create" role="main">
  <h1>${title}</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <g:hasErrors bean="${letterRefInstance}">
    <ul class="errors" role="alert">
      <g:eachError bean="${letterRefInstance}" var="error">
        <li <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>>
          <g:message error="${error}"/></li>
      </g:eachError>
    </ul>
  </g:hasErrors>
  <ingame:form url="[controller: 'letter', action: 'save']">
    <fieldset class="form">
      <g:render template="form"/>
    </fieldset>
    <fieldset class="buttons">
      <g:submitButton name="draft" class="save" value="Сохранить в черновики"/>
      <g:submitButton name="send" class="save" value="Отправить"/>
    </fieldset>
  </ingame:form>
</div>
</body>
</html>