<%@ page import="ru.srms.larp.platform.game.mail.LetterType; ru.srms.larp.platform.game.mail.LetterRef; ru.srms.larp.platform.game.mail.LetterContent" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="letter" value="${letterRefInstance as LetterRef}"/>
  <g:set var="title" value="${letter.content.subject}"/>
  <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </li>
  </ul>
</div>

<div id="show-letter" class="content scaffold-show" role="main">
  <h1>${title}</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <ol class="property-list letter">

    <li class="fieldcontain">
      <span id="time-label" class="property-label">Время отправки</span>
      <span class="property-value" aria-labelledby="time-label">
        <g:render template="/shared/date" model="[date: letter.content.time]"/></span>
    </li>

    <li class="fieldcontain">
      <span id="sender-label" class="property-label">От кого</span>
      <span class="property-value"
            aria-labelledby="sender-label">${letter.content.letterFrom}</span>
    </li>

    <li class="fieldcontain">
      <span id="recipients-label" class="property-label">Кому</span>
      <span class="property-value"
            aria-labelledby="recipients-label">${letter.content.letterTo}</span>
    </li>

    <li class="fieldcontain">
      <span id="subject-label" class="property-label">Тема</span>
      <span class="property-value"
            aria-labelledby="subject-label">${letter.content.subject}</span>

    </li>
    <li class="fieldcontain">
      <span id="text-label" class="property-label">Текст</span>
      <span class="property-value" aria-labelledby="text-label">${letter.content.text}</span>
    </li>

  </ol>
  <fieldset class="buttons".>
    <g:if test="${letter.type.equals(LetterType.DRAFT)}">
      <sec:permitted object="${letter.mailbox}" permission="create">
        <ingame:link controller="letter" class="edit" action="edit" id="${letter.id}">Редактировать</ingame:link>
        <ingame:link controller="letter" class="save" action="send" id="${letter.id}"
                     onclick="return confirm('Вы уверены?');">Отправить</ingame:link>
      </sec:permitted>
    </g:if>

    <sec:permitted object="${letter.mailbox}" permission="delete">
      <ingame:link controller="letter" class="delete" action="delete" id="${letter.id}"
                   onclick="return confirm('Вы уверены?');">Удалить</ingame:link>
    </sec:permitted>

  </fieldset>
</div>
</body>
</html>
