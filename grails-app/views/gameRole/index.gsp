<%@ page import="ru.srms.larp.platform.game.roles.GameRole" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${gameRoleInstanceList as List<GameRole>}"/>
  <g:set var="title" value="Игровые роли"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <ingame:link class="item" action="create"><i class="add green icon"></i> Добавить</ingame:link>
</content>

<content tag="content">
  <div class="ui grid">
    <div class="ten wide column">

      <div class="ui selection animated list">
        <g:render template="show" collection="${subject}" var="role"/>
      </div>

      <div class="ui right floated pagination menu">
        <ingame:semanticPaginate total="${rolesCount ?: 0}"/>
      </div>
    </div>
  </div>
</content>
</body>
</html>
