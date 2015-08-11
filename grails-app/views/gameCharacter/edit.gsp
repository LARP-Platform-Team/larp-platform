<%@ page import="ru.srms.larp.platform.game.character.GameCharacter; ru.srms.larp.platform.game.roles.GameRole;" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="/nested/contentWithActions">
  <g:set var="subject" value="${gameCharacterInstance as GameCharacter}"/>
  <g:set var="title" value="Редактирование персонажа"/>
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
      <ingame:form class="ui form" url="[resource: subject, action: 'update']">
        <g:render template="form"/>
        <g:hiddenField name="version" value="${subject?.version}"/>
        <ui:submit icon="checkmark">Сохранить</ui:submit>
      </ingame:form>
    </div>

    %{-- TODO может, в шаблон вынести всю эту конструкцию (в gameRole/edit так же) --}%
    <div class="eight wide column">
      <section class="ui pilled segment">
        <div class="ui blue ribbon label">Роли</div>
        <ingame:formRemote name="roles" url="[resource: subject, action: 'addRole']" method="POST"
                           update="[success: 'rolesContainer', failure: 'addRoleError']">

          <div id="rolesContainer">
            <g:render template="roles" model="[items: subject.roles]"/>
          </div>

          <div class="ui action input">
            <g:select name="role.id" from="${GameRole.findAllByGame(params.game)}"
                      optionKey="id" class="ui dropdown"/>
            <ui:submit class="right icon" icon="check circle outline" title="Назначить"/>
          </div>
        </ingame:formRemote>
      </section>
    </div>
  </div>
</content>

</body>
</html>
