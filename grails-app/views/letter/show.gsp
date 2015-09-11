<%@ page import="ru.srms.larp.platform.game.mail.LetterType; ru.srms.larp.platform.game.mail.LetterRef; ru.srms.larp.platform.game.mail.LetterContent" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions"/>
  <g:set var="subject" value="${letterRefInstance as LetterRef}"/>
  <g:set var="title" value="${subject.content.subject}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <ingame:link class="item" controller="MailBox" id="${subject.mailboxId}" action="show">
    <i class="arrow left grey icon"></i> Назад</ingame:link>
</content>

<content tag="content">
  <div class="ui fluid card">
    <div class="content">
      <div class="header">${subject.content.subject}</div>

      <div class="meta">
        <span>
          От кого: ${subject.content.letterFrom}
          <g:render template="/mailBox/addAddress" model="[letter: subject]"/>
        </span>

        <div class="right floated time">
          <g:render template="/shared/date" model="[date: subject.content.time]"/>
        </div>
      </div>

      <div class="meta">Кому: ${subject.content.letterTo}</div>

      <div class="description letterText richText">
        <hc:cleanHtml unsafe="${subject.content.text}" whitelist="rich-text"/>
      </div>
    </div>

    <div class="extra content">

      <g:if test="${subject.type.equals(LetterType.DRAFT)}">
        <sec:permitted object="${subject.mailbox}" permission="create">
          <ingame:link controller="letter" class="ui yellow basic icon labeled button"
                       action="edit"
                       id="${subject.id}">
            <i class="edit yellow icon"></i>Редактировать</ingame:link>
          <ingame:link controller="letter" class="ui blue basic icon labeled button" action="send"
                       id="${subject.id}"
                       onclick="return confirm('Вы уверены, что хотите отправить письмо?');">
            <i class="send blue icon"></i> Отправить</ingame:link>
        </sec:permitted>
      </g:if>

      <g:if test="${LetterType.INBOX.equals(subject.type)}">
        <sec:permitted object="${subject.mailbox}" permission="create">
          <ingame:link controller="letter" class="ui blue basic icon labeled button" action="compose"
                       params="['reply.id': subject.id, mailboxId: subject.mailboxId]">
            <i class="reply blue icon"></i> Ответить</ingame:link>
        </sec:permitted>
      </g:if>

      <g:if test="${!subject.deleted}">
        <sec:permitted object="${subject.mailbox}" permission="delete">
          <ingame:link controller="letter" class="ui red basic icon labeled button" action="delete"
                       id="${subject.id}"
                       onclick="return confirm('Вы уверены, что хотите удалить письмо?');">
            <i class="delete red icon"></i> Удалить</ingame:link>
        </sec:permitted>
      </g:if>
    </div>
  </div>

</content>
</body>
</html>
