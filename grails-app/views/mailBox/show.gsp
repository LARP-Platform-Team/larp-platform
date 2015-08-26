<%@ page import="ru.srms.larp.platform.game.mail.LetterType; ru.srms.larp.platform.game.mail.MailBox" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${mailBoxInstance as MailBox}"/>
  <g:set var="title" value="${subject.address}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">

  <sec:permitted object="${subject}" permission="create">
    <ingame:link class="item" controller="letter" action="compose" params="[mailboxId: subject.id]">
      <i class="write blue icon"></i> Написать письмо</ingame:link>
  </sec:permitted>

  <g:link mapping="playAs" params="[gameAlias: params.gameAlias, charAlias: params.charAlias]"
          class="item"><i
      class="arrow left grey icon"></i> Назад</g:link>
</content>

<content tag="content">

  <div class="ui grid">
    <div class="four wide column">
      <div class="ui vertical secondary fluid tabular pointing menu">
        <g:each in="${LetterType.values()}" var="tab" status="i">
          <a href="#" class="item ${i == 0 ? "active" : ""}"
             data-tab="tab-${tab.toString()}"><i class="${tab.icon} icon"></i> ${tab.title}
            <div
                class="ui ${letters.get(tab).size() == 0 ? "grey" : "green"} label">${letters.get(tab).size()}</div>
          </a>
        </g:each>
      </div>
    </div>

    <div class="twelve wide stretched column">
      <div class="ui segment">
        <g:each in="${LetterType.values()}" var="tab" status="i">
          <div class="ui tab ${i == 0 ? "active" : ""}" data-tab="tab-${tab.toString()}">
            <table class="ui celled padded very basic table">
              <thead>
              <tr>
                <th>От кого/Кому</th>
                <th>Тема</th>
              </tr>
              </thead>
              <tbody>
              <g:each in="${letters.get(tab)}" var="letter">
                <tr>
                <td>${tab.getAddress(letter.content)}</td>
                <td><ingame:link controller="letter" class="${letter.deleted ? 'deleted' : ''}"
                             action="show"
                             id="${letter.id}">${letter.content.subject}</ingame:link></td>
                </tr>
              </g:each>
              </tbody>
            </table>
          </div>
        </g:each>
      </div>
    </div>

  </div>
</content>
</body>
</html>
