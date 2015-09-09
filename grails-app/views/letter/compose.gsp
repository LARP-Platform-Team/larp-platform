<%@ page import="ru.srms.larp.platform.game.mail.LetterRef" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions"/>
  <g:set var="subject" value="${letterRefInstance as LetterRef}"/>
  <g:set var="title" value="Написать письмо"/>
  <title>${title}</title>
</head>

<content tag="actions">
  <ingame:link class="item" controller="MailBox" id="${params.mailboxId}" action="show">
    <i class="cancel grey icon"></i> Отмена</ingame:link>
</content>

<content tag="content">
  <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
  <g:render template="/shared/fromErrors" bean="${subject?.content}" var="subject"/>
  <ingame:form class="ui form" controller="letter" url="[id: subject.id, action: 'save']">
    <g:render template="form"/>
    <ui:submit name="draft" icon="add">Сохранить в черновики</ui:submit>
    <ui:submit name="send" icon="send">Отправить</ui:submit>
  </ingame:form>
</content>
</body>
</html>