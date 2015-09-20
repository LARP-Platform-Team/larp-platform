<%@ page import="ru.srms.larp.platform.game.character.GameCharacter; org.springframework.validation.FieldError; ru.srms.larp.platform.game.roles.GameRole" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${gameRoleInstance as GameRole}"/>
  <g:set var="title" value="Редактирование игровой роли ${subject.title}"/>
  <title>${title}</title>
</head>

<body>
<content tag="actions">
  <ingame:link class="item" action="index"><i class="cancel grey icon"></i> Отмена</ingame:link>
</content>

<content tag="content">
  <div class="ui two column stackable grid">
    <div class="eight wide column">
      <g:render template="/shared/fromErrors" bean="${subject}" var="subject"/>
      <ingame:form class="ui form" url="[resource: subject, action: 'save']">
        <g:render template="form"/>
        <g:hiddenField name="version" value="${subject?.version}"/>
        <ui:submit icon="checkmark">Сохранить</ui:submit>
      </ingame:form>
    </div>

    <div class="eight wide column">
      <section class="ui pilled segment">
        <div class="ui blue ribbon label">Персонажи</div>
        <ingame:formRemote name="characters" class="ui form" method="POST"
                           url="[resource: subject, action: 'addToChar']"
                           update="[success: 'charactersContainer', failure: 'addCharError']">

          <div id="charactersContainer" class="ui middle aligned selection list larp-ajax-container">
            <g:render template="characters" model="[characters: subject.characters]"/>
          </div>
          <div class="errors" id="addCharError"></div>
          <div class="ui divider"></div>

          <div class="ui action input">
            <g:select name="character.id" from="${GameCharacter.findAllByGame(params.game)}"
                      optionKey="id" class="ui dropdown"/>
            <ui:submit remote="true" class="right icon" icon="add user" title="Выбрать персонажа"/>
          </div>
        </ingame:formRemote>
      </section>
    </div>
  </div>
</content>
</body>
</html>
