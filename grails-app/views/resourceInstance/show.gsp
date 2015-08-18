<%@ page import="ru.srms.larp.platform.game.resources.ResourceInstance" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${resourceInstanceInstance as ResourceInstance}"/>
  <g:set var="title" value="${subject.title}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <g:link mapping="playAs" params="[gameAlias: params.gameAlias, charAlias: params.charAlias]"
          class="item"><i
      class="arrow left grey icon"></i> Назад</g:link>
</content>
<content tag="content">
  <div class="ui two column stackable grid">
    <div class="seven wide column">
      <section class="ui pilled segment">
        <div class="ui blue ribbon label">${subject.fullId}</div>

        <sec:permitted object="${subject}" permission="write">
          <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
          <ingame:form class="ui form" url="[resource: subject, action: 'changeValue']"
                       method="post">

            <div class="${hasErrors(bean: subject, field: 'value', 'error')} inline required field">
              <label for="value">Значение:</label>
              <g:textField name="value" required="" value="${subject?.value}"/>
              <g:if test="${subject.type.measure}">${subject.type.measure}</g:if>
            </div>
            <ui:submit name="update" icon="checkmark">Сохранить</ui:submit>
          </ingame:form>
        </sec:permitted>
        <sec:notPermitted object="${subject}" permission="write">
          <div class="ui hedaer">Текущее значение:</div>
          ${subject.value}<g:if test="${subject.type.measure}">${subject.type.measure}</g:if>
        </sec:notPermitted>
      </section>
    </div>

    <section class="nine wide column">
      <sec:permitted object="${subject}" permission="create">
        <section class="ui pilled segment">
          <div class="ui blue ribbon label">Произвести перевод</div>

          <div class="ui hidden divider"></div>

          <g:render template="/shared/fromErrors" bean="${transferData}" var="subject"/>
          <ingame:form class="ui form" url="[resource: subject, action: 'transfer']" method="post">
            <div class="fields">
              <div
                  class="${hasErrors(bean: transferData, field: 'transferValue', 'error')} required field">
                <label for="value">Количество:</label>
                <g:textField name="transferValue" required=""
                             placeholder="0 ${subject.type.measure}"
                             value="${transferData?.transferValue}"/>
              </div>

              <div
                  class="${hasErrors(bean: transferData, field: 'transferTargetId', 'error')} required field">
                <label for="value">Кому:</label>
                <g:textField name="transferTargetId" required=""
                             placeholder="${subject.type.identifierTitle}"
                             value="${transferData?.transferTargetId}"/>
              </div>
            </div>
            <ui:submit name="send" icon="right arrow">Отправить</ui:submit>
          </ingame:form>
        </section>
      </sec:permitted>

      <section class="ui pilled segment">
        <div class="ui blue ribbon label">История переводов</div>

        <div class="ui list">
          <g:each in="${subject.transferLogs}" var="log">
            <g:render template="transferLog" model="[log: log, parent: subject]"/>
          </g:each>
        </div>
      </section>
  </div>
</content>
</body>
</html>