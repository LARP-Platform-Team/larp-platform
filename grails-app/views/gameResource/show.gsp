<%@ page import="ru.srms.larp.platform.game.resources.GameResource; ru.srms.larp.platform.game.resources.ResourceInstance; org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="/nested/contentWithActions">
  <g:set var="subject" value="${gameResourceInstance as GameResource}"/>
  <g:set var="title" value="Настройка ресурса ${subject.title}"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <ingame:link class="item" controller="ResourceInstance" action="create" params="[typeId: subject.id]">
      <i class="green plus icon"></i> Создать экземпляр</ingame:link>
  <ingame:link class="item" action="index"><i class="arrow left grey icon"></i> Назад</ingame:link>
</content>

<content tag="content">

  <section>
    <h2>Экземпляры</h2>

    <g:if test="${subject.instances.size() == 0}">
      <ui:message type="info">Экземпляров ресурса данного типа пока нет.</ui:message>
    </g:if>
    <g:else>
      <table class="ui celled padded very basic table">
        <thead>
        <tr>
          <th>Название</th>
          <th>Значение</th>
          <th>Идентификатор</th>
          <th>Владелец</th>
          <th>Источник</th>
          <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${subject.instances}" var="item">
          <tr>
            <td>${item.title}</td>
            <td>${item.value} ${item.type.measure}</td>
            <td>${item.fullId}</td>
            <td>${item.owner}</td>
            <td>${item.origin?.title}</td>
            <td class="buttons">
              <ingame:link action="edit" resource="${item}" class="ui yellow icon basic button"
                           title="Редактировать">
                <i class="yellow edit icon"></i></ingame:link>
              <ingame:link action="delete" resource="${item}" class="ui red icon basic button"
                           title="Удалить"
                           onclick="return confirm('Вы уверены?');"><i
                  class="red delete icon"></i></ingame:link>
            </td>
          </tr>
        </g:each>
        </tbody>
        <g:render template="/shared/semantic/tablePaginate"
                  model="[colspan: 6, itemsQty: itemsCount]"/>
      </table>
    </g:else>

  </section>

  <br/><!-- sorry for that-->

  <section class="ui pilled segment">
    <h2>Источники</h2>
    <ingame:formRemote name="origins" class="ui form"
                       url="[resource: subject, action: 'addOrigin']" method="POST"
                       update="[success: 'itemsContainer', failure: 'addOriginError']">

      <div id="itemsContainer">
        <g:render template="origins" model="[items: subject.origins]"/>
      </div>

      <div class="ui grid">
      <div class="eight wide column">
        <div class="ui action input">
          <g:textField name="originTitle" id="newOriginTitle" required="required"/>
          <ui:submit class="right" icon="plus">Добавить</ui:submit>
        </div>
        </div>
      </div>

    </ingame:formRemote>
  </section>

</content>
</body>
</html>
