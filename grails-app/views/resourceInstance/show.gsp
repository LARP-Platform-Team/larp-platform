<%@ page import="ru.srms.larp.platform.game.resources.ResourceInstance; org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="subject" value="${resourceInstanceInstance as ResourceInstance}"/>
  <g:set var="title" value="${subject.title}"/>
  <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </li>
  </ul>
</div>

<div class="content scaffold-create" role="main">
  <h1>${title} (${subject.fullId})</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>

  <div class="property-list">
    <div>Текущее значение:
      <sec:permitted object="${subject}" permission="write">
        <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
        <ingame:form url="[resource: subject, action: 'changeValue']" method="post">
          <fieldset class="form">
            <div class="fieldcontain ${hasErrors(bean: subject, field: 'value', 'error')}">
              <g:textField name="value" required="" value="${subject?.value}"/>
              <g:if test="${subject.type.measure}">${subject.type.measure}</g:if>
            </div>
            <g:submitButton name="update" class="save" value="Сохранить"/>
          </fieldset>
        </ingame:form>
      </sec:permitted>
      <sec:notPermitted object="${subject}" permission="write">
        ${subject.value}<g:if test="${subject.type.measure}">${subject.type.measure}</g:if>
      </sec:notPermitted>
    </div>
  </div>

  <sec:permitted object="${subject}" permission="create">
    <div class="property-list">
      <div>Произвести перевод</div>

      <g:render template="/shared/fromErrors" bean="${transferData}" var="subject"/>
      <ingame:form url="[resource: subject, action: 'transfer']" method="post">
        <fieldset class="form">

          <div class="fieldcontain ${hasErrors(bean: transferData, field: 'transferValue', 'error')}">
            <label for="value">Количество</label>
            <g:textField name="transferValue" required="" value="${transferData?.transferValue}"/>
          </div>

          <div class="fieldcontain ${hasErrors(bean: transferData, field: 'transferTargetId', 'error')}">
            <label for="value">Кому</label>
            <g:textField name="transferTargetId" required="" value="${transferData?.transferTargetId}"/>
          </div>

          <g:submitButton name="send" class="save" value="Отправить"/>

        </fieldset>
      </ingame:form>

    </div>
  </sec:permitted>

  <section>
    <h1>История переводов</h1>
    <ul class="property-list">
    <g:each in="${subject.transferLogs}" var="log">
      <li><g:render template="transferLog" model="[log: log, parent: subject]"/></li>
    </g:each>
    </ul>
  </section>
</div>
</body>
</html>