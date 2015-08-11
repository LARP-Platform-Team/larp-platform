<%@ page import="ru.srms.larp.platform.game.news.NewsFeed" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="/nested/contentWithActions">
  <g:set var="subject" value="${feeds as List<NewsFeed>}"/>
  <g:set var="title" value="Новостные ленты"/>
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
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${feeds}" var="item">
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
              model="[colspan: 2, itemsQty: feedsCount]"/>
  </table>

</content>
</body>
</html>
