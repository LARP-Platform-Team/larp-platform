<%@ page import="ru.srms.larp.platform.game.character.GameCharacter; ru.srms.larp.platform.game.resources.GameResource; ru.srms.larp.platform.game.resources.ResourceInstance; org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${gameResourceInstance as GameResource}"/>
  <g:set var="title" value="Настройка ресурса ${subject.title}"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <ingame:link class="item" controller="ResourceInstance" action="create"
               params="[typeId: subject.id]">
    <i class="green plus icon"></i> Создать хранилище</ingame:link>
  <ingame:link class="item" action="index"><i class="arrow left grey icon"></i> Назад</ingame:link>
</content>

<content tag="content">

  <section>
    <h2>Хранилища</h2>


    <div class="ui form">
      <ingame:form url="[resource: subject, action: 'show']">
        <div class="inline field">
          <label for="characterFilter">Найти по персонажу-владельцу:</label>
          <g:select name="characterFilter" from="${GameCharacter.findAllByGame(params.game)}"
                    optionKey="id" class="ui dropdown" value="${params?.characterFilter}" noSelection="${['0': 'Без владельца']}"/>
          <ui:submit class="search icon" icon="search" title="Найти"/>
          <g:if test="${params.containsKey('characterFilter')}">
          <ingame:link resource="${subject}" action="show" class="ui icon button" title="Сбросить фильтр">
            <i class="ui remove icon"></i></ingame:link>
          </g:if>
        </div>
      </ingame:form>
    </div>

    <g:if test="${subject.instances.size() == 0}">
      <ui:message type="info">Хранилищ ресурса данного типа пока нет.</ui:message>
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
          <g:if test="${!params.containsKey('characterFilter') ||
              (!item.owner && params.getInt('characterFilter') == 0) ||
              (item.owner && item.owner.id == params.getLong('characterFilter'))}">
            <tr>
              <td>${item.type.storage}</td>
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
          </g:if>
        </g:each>
        </tbody>
        <g:render template="/shared/semantic/tablePaginate"
                  model="[colspan: 6, itemsQty: itemsCount]"/>
      </table>
    </g:else>

  </section>

  <div class="ui hidden divider"></div>

  <section class="ui pilled segment">
    <h2>Источники ресурса (например, банки)</h2>
    <ingame:formRemote name="origins" class="ui form"
                       url="[resource: subject, action: 'addOrigin']" method="POST"
                       update="[success: 'itemsContainer', failure: 'addOriginError']">

      <div id="itemsContainer" class="ui middle aligned horizontal relaxed divided selection list larp-ajax-container">
        <g:render template="origins" model="[items: subject.origins]"/>
      </div>
      <div id="addOriginError"></div>
      <div class="ui divider"></div>

      <div class="ui grid">
        <div class="eight wide column">
          <div class="ui action input">
            <g:textField name="originTitle" placeholder="Введите название" id="newOriginTitle"
                         required="required"/>
            <ui:submit remote="true" class="right" icon="plus">Добавить</ui:submit>
          </div>

          <div class="ui pointing label">Максимум 64 символа. Уникальное в рамках ресурса.</div>
        </div>
      </div>

    </ingame:formRemote>
  </section>

</content>
</body>
</html>
