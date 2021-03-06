<%@ page import="ru.srms.larp.platform.game.character.GameCharacter" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="mainWithActions">
    <g:set var="subject" value="${gameCharacterInstance as GameCharacter}"/>
    <g:set var="title" value="Создание персонажа"/>
    <title>${title}</title>
</head>

<body>

<content tag="actions">
    <ingame:link class="item" action="index"><i class="cancel grey icon"></i> Отмена</ingame:link>
</content>

<content tag="content">
    <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
    <ingame:form class="ui form" url="[resource: subject, action: 'save']">
        <g:render template="form" model="[isCreate: true]"/>
        <ui:submit icon="add">Сохранить</ui:submit>
    </ingame:form>
</content>
</body>
</html>
