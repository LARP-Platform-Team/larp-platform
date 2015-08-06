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
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </li>
  </ul>
</div>
<div class="message">Пока для отображения пользователя используется логин, но потом будет нормальное имя.</div>

<div class="content scaffold-create" role="main">
  <h1>${title}</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>

  <div class="property-list">
    <div>Пока единственное, что можно показать - почта. ${subject.email}. Потом, конечно, она показываться не будет кому попало.</div>
    <div>
      <sec:access expression="isFullyAuthenticated() and (hasRole('ROLE_ADMIN') or principal.username == '${subject.username}')">
        <g:render template="/shared/fromErrors" bean="${subject}" var="item"/>
        <ingame:form url="[id: subject.id, controller: 'account', action: 'update']" method="post">
          <g:hiddenField name="version" value="${subject?.version}"/>
          <fieldset class="form">
            <g:render template="form"/>
          </fieldset>
          <fieldset class="buttons">
            <g:actionSubmit class="save" action="update" value="Сохранить"/>
          </fieldset>
        </ingame:form>
      </sec:access>
    </div>
  </div>

  <section>
    <h1>Мастерит</h1>
    <ul class="property-list">
      <g:each in="${masters}" var="game">
        <li><g:link resource="${game}">${game.title}</g:link> </li>
      </g:each>
    </ul>
  </section>

  <section>
    <h1>Играет</h1>
    <ul class="property-list">
      <g:each in="${plays}" var="game">
        <li><g:link resource="${game}">${game.title}</g:link> </li>
      </g:each>
    </ul>
  </section>

</div>
</body>
</html>