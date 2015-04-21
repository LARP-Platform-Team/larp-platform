<%@ page import="ru.srms.larp.platform.game.mail.LetterType; ru.srms.larp.platform.game.mail.MailBox" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="mailbox" value="${mailBoxInstance as MailBox}"/>
  <g:set var="title" value="${mailbox?.address}"/>
  <title>${title}</title>
</head>

<body>
<div class="nav" role="navigation">
  <ul>
    <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </li>
    <sec:permitted object="${mailbox}" permission="create">
      <li><ingame:link class="create" controller="letter" action="compose"
                       params="[mailboxId: mailbox.id]">
        Написать письмо
      </ingame:link></li>
    </sec:permitted>
  </ul>
</div>

<div id="show-mailBox" class="content scaffold-show" role="main">
  <h1>${title}</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <ol class="property-list mailBox">
    <g:each in="${letters}" var="item">
      <g:set var="letterType" value="${item.key as LetterType}"/>
      <br/>${letterType} (${item.value.size()}):
      <table><tr>
        <th></th>
        <th>Адрес</th>
        <th>Тема</th>
        <th>Действия</th>
      </tr>
        <g:each in="${item.value}" var="letter">
          <tr>
            <td><g:checkBox name="tt"/></td>
            <td>${letterType.getAddress(letter.content)}</td>
            <td>
              <ingame:link controller="letter" class="${letter.deleted ? 'deleted' : ''}" action="show"
              id="${letter.id}">${letter.content.subject}</ingame:link>
            </td>
            <td></td>
          </tr>
        </g:each>
      </table>
    </g:each>
  </ol>

</div>
</body>
</html>
