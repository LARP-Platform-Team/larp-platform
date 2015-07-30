<%@ page import="org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="title" value="Редактирование ресурса ${gameResourceInstance?.title}"/>
    <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </li>
        <li><ingame:link class="list" action="index">Все ресурсы</ingame:link></li>
    </ul>
</div>

<div id="create-mailBox" class="content scaffold-create" role="main">
    <h1>${title}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:render template="/shared/fromErrors" bean="gameResourceInstance" var="item"/>
    <ingame:form url="[resource: gameResourceInstance, action: 'update']" method="post">
        <g:hiddenField name="version" value="${gameResourceInstance?.version}"/>
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
