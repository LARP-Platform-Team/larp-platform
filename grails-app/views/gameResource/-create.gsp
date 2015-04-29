<%@ page import="org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="Создать новый ресурс"/>
  <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </li>
    <li><ingame:link class="list" action="index"><g:message code="default.list.label"
                                                            args="[entityName]"/></ingame:link></li>
  </ul>
</div>

<div id="create-resource" class="content scaffold-create" role="main">
  <h1>${title}</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <g:hasErrors bean="${resourceInstance}">
    <ul class="errors" role="alert">
    <g:eachError bean="${resourceInstance}" var="error">
      <li<g:if
        test="${error in FieldError}">data-field-id="${error.field}"</g:if>>
      <g:message error="${error}"/>
      </li>
    </g:eachError>
    </ul>
  </g:hasErrors>
  <ingame:form url="[resource: resourceInstance, action: 'save']">
    <fieldset class="form">
      <g:render template="form"/>
    </fieldset>
    <fieldset class="buttons">
      <g:submitButton name="create" class="save" value="Сохранить"/>
    </fieldset>
  </ingame:form>
</div>
</body>
</html>
