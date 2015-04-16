<%@ page import="org.springframework.validation.FieldError; ru.srms.larp.platform.game.mail.MailBox" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="${mailBoxInstance.address}"/>
  <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </li>
    <li><ingame:link class="list" action="index">Все ящики</ingame:link></li>
    <li><ingame:link class="create" action="create">Добавить</ingame:link></li>
  </ul>
</div>

<div id="edit-mailBox" class="content scaffold-edit" role="main">
  <h1>${title}</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <g:hasErrors bean="${mailBoxInstance}">
    <ul class="errors" role="alert">
      <g:eachError bean="${mailBoxInstance}" var="error">
        <li<g:if
               test="${error in FieldError}">data-field-id="${error.field}"</g:if>>
        <g:message error="${error}"/>
        </li>
      </g:eachError>
    </ul>
  </g:hasErrors>
  <ingame:form url="[resource: mailBoxInstance, action: 'update']" method="post">
    <g:hiddenField name="version" value="${mailBoxInstance?.version}"/>
    <fieldset class="form">
      <g:render template="form"/>
    </fieldset>
    <fieldset class="buttons">
      <g:actionSubmit class="save" action="update" value="Сохранить"/>
    </fieldset>
  </ingame:form>
</div>
</body>
</html>
