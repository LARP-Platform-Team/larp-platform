<%@ page import="ru.srms.larp.platform.game.character.GameCharacter" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="mainWithActions">
  <g:set var="subject" value="${character as GameCharacter}"/>
  <g:set var="title" value="Приветствуем, ${subject.name}!"/>
  <title>${title}</title>
</head>

<body>

<content tag="actions">
  <g:link mapping="game" params="[gameAlias: params.gameAlias]" class="item"><i
      class="arrow left grey icon"></i> Назад</g:link>
</content>

<content tag="content">
  <g:if test="${!character.isDead}">
    <div class="ui black ribbon label" title="Пресонаж мертв, увы :("><i class="big white frown icon"></i> R.I.P.</div>
    <div class="ui hidden divider"></div>
  </g:if>


  <div class="ui two column relaxed grid">
    <div class="column">
      <section class="ui pilled segment">
        <div class="ui blue ribbon label">Роли</div>

        <div class="ui divided horizontal list">

          <g:each in="${character.roles}" var="role">
            <div class="item">
              <div class="title">${role.title}</div>
            </div>
          </g:each>
        </div>
      </section>

    </div>

    <div class="column">
      <g:newsFeeds/>
    </div>

    <div class="column">
      <g:availableMailboxes/>
    </div>

    <div class="column">
      <g:availableResources/>
    </div>

  </div>
</content>
</body>
</html>
