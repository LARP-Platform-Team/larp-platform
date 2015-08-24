<%@ page import="ru.srms.larp.platform.game.mail.MailBox" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${mailBoxInstanceList as List<MailBox>}"/>
  <g:set var="title" value="Почтовые ящики"/>
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
      <th>Адрес</th>
      <th>Владелец</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${subject}" var="item">
      <tr>
        <td><ingame:link action="show" id="${item.id}">${item.toString()}</ingame:link></td>
        <td>${item.owner}</td>
        <td class="buttons">
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
              model="[colspan: 3, itemsQty: itemsCount]"/>
  </table>
</content>

</body>
</html>
