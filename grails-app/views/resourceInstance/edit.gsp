<%@ page import="ru.srms.larp.platform.game.resources.ResourceInstance; org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${resourceInstanceInstance as ResourceInstance}"/>
  <g:set var="title" value="Редактирование хранилища для ресурса ${subject.type.title}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <ingame:link class="item" action="show" resource="${subject.type}">
    <i class="cancel grey icon"></i> Отмена</ingame:link>

  <ingame:link class="item" controller="ResourcePeriodicRule" action="create"
               params="['target.id': subject.id]">
    <i class="green wait icon"></i> Создать периодическое правило</ingame:link>
</content>

<content tag="content">
  <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
  <ingame:form class="ui form" url="[resource: subject, action: 'update']">
    <g:render template="form"/>
    <g:hiddenField name="version" value="${subject?.version}"/>
    <ui:submit icon="checkmark">Сохранить</ui:submit>
  </ingame:form>

  <section class="ui pilled segment">
    <div class="ui blue ribbon label">Правила периодического изменения</div>

    <div class="ui hidden divider"></div>
    <g:if test="${!subject.periodicRules.size()}">
      <ui:message type="info">Список правил пуст.</ui:message>
    </g:if>
    <div class="ui grid">
      <div class="seven wide column">
        <div class="ui middle aligned selection divided list">
          <g:each in="${subject.periodicRules}" var="rule">
            <g:render template="/resourcePeriodicRule/show" model="[item: rule]"/>
          </g:each>
        </div>
      </div>
    </div>
  </section>
</content>
</body>
</html>
