<%@ page import="ru.srms.larp.platform.game.resources.ResourceInstance; org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="subject" value="${resourceInstanceInstance as ResourceInstance}"/>
    <g:set var="title" value="Редактирование ресурса ${subject?.title}"/>
    <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </li>
        <li><ingame:link class="list" controller="GameResource"  action="index">Все ресурсы</ingame:link></li>
        <li><ingame:link class="list" controller="GameResource" action="show"
                         resource="${subject.type}">${subject.type.title}</ingame:link></li>
    </ul>
</div>

<div id="create-mailBox" class="content scaffold-create" role="main">
    <h1>${title}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:render template="/shared/fromErrors" bean="${subject}" var="item"/>
    <ingame:form url="[resource: subject, action: 'update']" method="post">
        <g:hiddenField name="version" value="${subject?.version}"/>
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
