<%@ page import="ru.srms.larp.platform.game.resources.GameResource" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="/nested/contentWithActions">
  <g:set var="subject" value="${gameResourceInstanceList as List<GameResource>}"/>
  <g:set var="title" value="Игровые ресурсы"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <ingame:link class="item" action="create"><i class="add green icon"></i> Добавить</ingame:link>
</content>

<content tag="content">
  <table class="ui celled padded very basic table">
    <thead>
    <tr>
      <th>Название</th>
      <th>Ед. изм.</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${subject}" var="item">
      <tr>
        <td><ingame:link action="show" id="${item.id}">${item.title}</ingame:link></td>
        <td>${item.measure}</td>
        <td>
          <ingame:link action="show" id="${item.id}" class="ui blue icon basic button"
                       title="Настроить"><i class="blue setting icon"></i></ingame:link>
          <ingame:link action="edit" id="${item.id}" class="ui yellow icon basic button"
                       title="Редактировать"><i class="yellow edit icon"></i></ingame:link>
          <ingame:link action="delete" id="${item.id}" class="ui red icon basic button"
                       title="Удалить" onclick="return confirm('Вы уверены?');">
            <i class="red delete icon"></i></ingame:link>
        </td>
      </tr>
    </g:each>
    </tbody>
    <g:render template="/shared/semantic/tablePaginate" model="[colspan: 3, itemsQty: itemsCount]"/>
  </table>
</content>
</body>
</html>
