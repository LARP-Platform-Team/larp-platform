<%@ page import="ru.srms.larp.platform.sec.SpringUser; ru.srms.larp.platform.game.resources.ResourceInstance; org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="subject" value="${springUserInstance as SpringUser}"/>
  <g:set var="title" value="${subject.username}"/>
  <title>${title}</title>
</head>

<body>
<content tag="content">

  <ui:message
      type="warning">Пока для отображения пользователя используется логин, но потом будет нормальное имя.</ui:message>


  <ui:message type="info">
    Тут будет какая-то публичная информация.
  </ui:message>

  <div class="ui two column grid">

    <sec:access
        expression="isFullyAuthenticated() and (hasRole('ROLE_ADMIN') or principal.username == '${subject.username}')">

      <div class="column">
        <section class="ui pilled segment">
          <div class="ui blue ribbon label">Изменить личные данные</div>

          <div class="ui hidden divider"></div>
          <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
          <ingame:form url="[id: subject.id, controller: 'account', action: 'update']"
                       class="ui form"
                       method="post">
            <g:hiddenField name="version" value="${subject?.version}"/>
            <g:render template="form"/>
            <ui:submit icon="checkmark">Сохранить</ui:submit>
          </ingame:form>
        </section>
      </div>

      <div class="column">
        <section class="ui pilled segment">
          <div class="ui blue ribbon label">Изменить пароль</div>

          <div class="ui hidden divider"></div>
          <g:render template="/shared/fromErrors" bean="${changePassword}" var="subject"/>
          <ingame:form url="[id: subject.id, controller: 'account', action: 'changePassword']"
                       class="ui form"
                       method="post">
            <g:hiddenField name="version" value="${subject?.version}"/>
            <g:render template="changePasswordForm"/>
            <ui:submit icon="checkmark">Сохранить</ui:submit>
          </ingame:form>
        </section>
      </div>
    </sec:access>
    <div class="column">
      <section class="ui pilled segment">
        <div class="ui red ribbon label">Мастерит</div>
        <g:if test="${masters}">
          <ul class="ui list">
            <g:each in="${masters}" var="game">
              <li class="item"><g:link resource="${game}">${game.title}</g:link></li>
            </g:each>
          </ul>
        </g:if>
        <g:else>
          <ui:message type="info">Этот пользователь не является мастером игр</ui:message>
        </g:else>
      </section>
    </div>

    <div class="column">
      <section class="ui pilled segment">
        <div class="ui green ribbon label">Играет</div>
        <g:if test="${plays}">
          <ul class="ui list">
            <g:each in="${plays}" var="game">
              <li class="item"><g:link resource="${game}">${game.title}</g:link></li>
            </g:each>
          </ul>
        </g:if>
        <g:else>
          <ui:message type="info">Этот пользователь не участвует ни в одной игре</ui:message>
        </g:else>
      </section>
    </div>
  </div>
</content>
</body>
</html>