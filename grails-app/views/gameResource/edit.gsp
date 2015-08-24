<%@ page import="ru.srms.larp.platform.game.resources.GameResource; org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${gameResourceInstance as GameResource}"/>
  <g:set var="title" value="Редактирование ресурса ${subject.title}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <ingame:link class="item" action="index"><i class="cancel grey icon"></i> Отмена</ingame:link>
</content>

<content tag="content">
  <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
  <ingame:form class="ui form" url="[resource: subject, action: 'update']">
    <div class="ui three fields">
      <g:render template="form"/>
    </div>
    <g:hiddenField name="version" value="${subject?.version}"/>
    <ui:submit icon="checkmark">Сохранить</ui:submit>
  </ingame:form>
</content>
</body>
</html>
