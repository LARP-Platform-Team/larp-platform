<%@ page import="ru.srms.larp.platform.game.character.request.RequestFormField" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${requestFormFieldInstanceList as List<RequestFormField>}"/>
  <g:set var="title" value="Поля анкеты игрока"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <ingame:link class="item" action="create"
               params="${params.role?.id ? ['role.id': params.role?.id] : [:]}"><i class="add green icon"></i> Добавить</ingame:link>
</content>

<content tag="content">

  <table class="ui celled padded very basic table">
    <thead>
    <tr>
      <th>Название</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${subject}" var="item">
      <tr>
        <td><ingame:link action="show" id="${item.id}">${item.title}</ingame:link></td>
        <td>
          <ingame:link action="edit" id="${item.id}" class="ui yellow icon basic button"
                       title="Редактировать"><i class="yellow edit icon"></i></ingame:link>
          <ingame:link action="delete" id="${item.id}" class="ui red icon basic button"
                       title="Удалить" onclick="return confirm('Вы уверены?');">
            <i class="red delete icon"></i></ingame:link>
        </td>
      </tr>
    </g:each>
    </tbody>
    <g:render template="/shared/semantic/tablePaginate"
              model="[colspan: 2, itemsQty: itemsQty]"/>
  </table>

</content>
</body>
</html>
