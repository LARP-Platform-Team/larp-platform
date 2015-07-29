<%@ page import="ru.srms.larp.platform.game.resources.GameResource; ru.srms.larp.platform.game.resources.ResourceInstance; org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="Ресурс ${gameResourceInstance?.title}"/>
  <g:set var="subject" value="${gameResourceInstance as GameResource}"/>
  <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </li>
    <li><ingame:link class="list" action="index">Все ресурсы</ingame:link></li>
  </ul>
</div>

<div id="show-gameResource" class="content property-list scaffold-create" role="main">
  <h1>${title}</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>

  <section>
    <h2>Экземпляры</h2>

    <div class="nav" style="display: inline-block"><ingame:link class="create" controller="ResourceInstance" params="[typeId: subject.id]"
                                                                action="create">Добавить</ingame:link></div>

    <div>
      <g:if test="${subject.instances.size() == 0}">Нет</g:if>
      <g:else>
            <table>
              <thead>
              <tr>
                <g:sortableColumn property="title" title="Название"/>
                <g:sortableColumn property="value" title="Значение"/>
                <th>Идентификатор</th>
                <g:sortableColumn property="owner" title="Владелец"/>
                <g:sortableColumn property="origin" title="Источник"/>
                <th>Действия</th>
              </tr>
              </thead>
              <tbody>
              <g:each in="${subject.instances}" status="i" var="instance">

                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                  <td><ingame:link action="show"
                                   id="${instance.id}">${instance.title}</ingame:link></td>
                  <td>${instance.value}</td>
                  <td>${instance.identifierTitle}: ${instance.identifier}</td>
                  <td>${instance.owner}</td>
                  <td>${instance.origin?.title}</td>
                  <td class="buttons">
                    <ingame:link class="edit" action="edit" resource="${instance}">Редактировать</ingame:link>
                    <ingame:link class="delete" action="delete" resource="${instance}">Удалить</ingame:link>
                  </td>
                </tr>
              </g:each>
              </tbody>
            </table>
      </g:else>
    </div>

  </section>

  <br/><!-- sorry for that-->

  <section>
    <h2>Источники</h2>
    <ingame:formRemote name="origins"
                       url="[resource: gameResourceInstance, action: 'addOrigin']"
                       update="[success: 'resource-origins', failure: 'addOriginError']"
                       method="POST">

      <fieldset class="form">
        <div class="fieldcontain">
          <label>Существующие</label>
          <g:render template="origins" model="[origins: gameResourceInstance.origins]"/>
        </div>

        <div class="fieldcontain">
          <label for="newOriginTitle">Название источника:</label>
          <g:textField name="originTitle" id="newOriginTitle" required="required"/>
        </div>

        <g:submitButton name="addOrigin" value="Добавить"/>
      </fieldset>

    </ingame:formRemote>
  </section>

</div>
</body>
</html>
