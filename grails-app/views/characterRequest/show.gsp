<%@ page import="ru.srms.larp.platform.game.character.request.FormFieldValue; ru.srms.larp.platform.game.roles.GameRole; ru.srms.larp.platform.game.character.request.RequestFormField; ru.srms.larp.platform.game.character.GameCharacter; ru.srms.larp.platform.game.character.request.RequestStatus; ru.srms.larp.platform.game.character.request.CharacterRequest" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${characterRequestInstance as CharacterRequest}"/>
  <g:set var="title" value="Заявка от игрока ${subject.user.name}"/>

  <g:set var="gameFields" value="${gameFields as List<RequestFormField>}"/>
  <g:set var="roles" value="${roles as Map<GameRole, List<RequestFormField>>}"/>
  <g:set var="values" value="${values as Map<Long, FormFieldValue>}"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <sec:notPermitted object="${subject.game}" permission="administration">
    <ingame:link class="item" mapping="game"><i class="arrow left grey icon"></i> Назад</ingame:link>
  </sec:notPermitted>
  <sec:permitted object="${subject.game}" permission="administration">
    <ingame:link class="item" action="index"><i class="arrow left grey icon"></i> Назад</ingame:link>
  </sec:permitted>
</content>

<content tag="content">

  <g:render template="status" model="[status: subject.status]"/>
  <div class="ui hidden divider"></div>

  <g:if test="${subject.character}">
    <g:link mapping="playAs" class="ui green basic button"
            params="[charAlias: subject.character.alias, gameAlias: subject.character.game.alias]">
      Играть за персонажа: ${subject.character.name}
    </g:link>
  </g:if>

  <g:if test="${subject.comment}">
    <ui:message type="warning" title="Комментарий мастера">
      <hc:cleanHtml unsafe="${subject.comment}" whitelist="simple-rich-text"/>
    </ui:message>
  </g:if>

  %{-- Game fields --}%
  <div class="ui horizontal  divider">Общая анкета</div>
  <g:render template="fieldValuesList" model="[fields: gameFields]"/>

  %{-- Roles fields --}%
  <div class="ui horizontal  divider">Желаемые роли</div>
  <div class="ui stackable grid">
    <g:each in="${roles}" var="roleFields">
      <div class="ui eight wide column">
        <div class="ui pilled segment">
          <h4>${roleFields.key.title}</h4>
          <g:render template="fieldValuesList" model="[fields: roleFields.value]"/>
        </div>
      </div>
    </g:each>
  </div>

  <g:if test="${!subject.status.data.editable && subject.status.data.nextAllowed}">
    <sec:permitted object="${subject.game}" permission="administration">
      <div class="ui horizontal  divider">Перевести в состояние:</div>
      <ingame:form class="ui form" url="[action: 'changeState', id: subject.id]">
        <div>
          <g:each in="${subject.status.data.nextAllowed}" var="newStatus">
            <ui:submit class="${newStatus.data.labelColor}" name="request_new_status"
                       value="${newStatus.toString()}">${newStatus.data.name}</ui:submit>
          </g:each>
        </div>
        <div class="ui hidden divider"></div>

        <g:if test="${subject.status.data.selectCharacter}">
          <div class="field">
            <label for="request_comment">Созданный персонаж:</label>
            <g:select name="request_character_id" optionKey="id" class="ui dropdown"
                      from="${GameCharacter.findAllByGameAndPlayer(params.game, null)}"
                      noSelection="[null: '']"/>
            <div
                class="ui basic red pointing label">При выборе персонажа он автоматически назначится автору заявки</div>
          </div>
        </g:if>

        <div class="field">
          <label for="request_comment">Комментарий:</label>
          <g:textArea class="simple rich" name="request_comment" value=""/>
        </div>
      </ingame:form>
    </sec:permitted>
  </g:if>
</content>

</body>
</html>