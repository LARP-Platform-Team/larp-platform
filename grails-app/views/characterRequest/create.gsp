<%@ page import="ru.srms.larp.platform.game.character.request.CharacterRequest" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${characterRequestInstance as CharacterRequest}"/>
  <g:set var="title" value="Создание заявки"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <ingame:link class="item" action="index"><i class="cancel grey icon"></i> Отмена</ingame:link>
</content>

<content tag="content">
  <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
  <ingame:form class="ui form" url="[action: 'save']">
    <g:render template="form"/>
    <ui:submit icon="add" name="saveRequestToDrafts">Сохранить в черновики</ui:submit>
    <ui:submit icon="send" name="sendCharacterRequest">Отправить на рассмотрение</ui:submit>
  </ingame:form>
</content>

</body>
</html>
