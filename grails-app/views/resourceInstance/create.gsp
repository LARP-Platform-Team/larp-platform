<%@ page import="ru.srms.larp.platform.game.resources.ResourceInstance;" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${resourceInstanceInstance as ResourceInstance}"/>
  <g:set var="title" value="Создание хранилища для ресурса типа ${subject.type.title}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <ingame:link class="item" action="show" resource="${subject.type}">
    <i class="cancel grey icon"></i> Отмена</ingame:link>
</content>

<content tag="content">
  <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
  <ingame:form class="ui form" url="[resource: subject, action: 'save']">
    <g:render template="form"/>
    <ui:submit class="primary" icon="add">Сохранить</ui:submit>
  </ingame:form>
</content>

</body>
</html>
