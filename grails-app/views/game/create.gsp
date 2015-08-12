<%@ page import="org.springframework.validation.FieldError; ru.srms.larp.platform.game.Game" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="subject" value="${gameInstance as Game}"/>
    <g:set var="title" value="Создать новую игру"/>
    <title>${title}</title>
</head>

<body>
<content tag="content">
    <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
    <g:form class="ui form" url="[resource: subject, action: 'save']" method="POST">
        <g:render template="form"/>
        <ui:submit icon="checkmark">Сохранить</ui:submit>
    </g:form>
</content>
</body>
</html>
