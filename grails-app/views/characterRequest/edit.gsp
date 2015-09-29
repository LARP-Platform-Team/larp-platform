<%@ page import="ru.srms.larp.platform.game.character.request.CharacterRequest" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${characterRequestInstance as CharacterRequest}"/>
  <g:set var="title" value="Редактирование заявки"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <ingame:link class="item" mapping="game"><i class="cancel grey icon"></i> Отмена</ingame:link>
</content>

<content tag="content">
  <g:render template="status" model="[status: subject.status]"/>
  <div class="ui hidden divider"></div>

  <g:if test="${subject.comment}">
    <ui:message type="warning" title="Комментарий мастера">
      <hc:cleanHtml unsafe="${subject.comment}" whitelist="simple-rich-text"/>
    </ui:message>
  </g:if>

  <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
  <ingame:form class="ui form" url="[id: subject.id, action: 'update']">
    <g:render template="form"/>
    <g:hiddenField name="version" value="${subject?.version}"/>
    <ui:submit icon="checkmark" name="saveRequestToDrafts">Сохранить в черновики</ui:submit>
    <ui:submit icon="send" name="sendCharacterRequest">Отправить на рассмотрение</ui:submit>
  </ingame:form>
</content>

</body>
</html>