<%@ page import="ru.srms.larp.platform.game.resources.PeriodicRule" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${periodicRuleInstance as PeriodicRule}"/>
  <g:set var="title" value="Создание правила обновлнеия для ресурса ${subject.target.fullId}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <ingame:link class="item" action="edit" resource="${subject.target}">
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
