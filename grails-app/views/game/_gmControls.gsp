<%@ page import="ru.srms.larp.platform.game.Game" %>
<g:link class="item" action="edit" resource="${game}"><i class="settings icon"></i> Параметры</g:link>
<ingame:link class="item" controller="gameCharacter" action="index"><i class="users icon"></i> Персонажи</ingame:link>
<ingame:link class="item" controller="gameRole" action="index"><i class="sitemap icon"></i> Роли</ingame:link>
<g:if test="${params.game.modules.contains(Game.GameModule.NEWS)}">
  <ingame:link class="item" controller="newsFeed" action="index"><i class="newspaper icon"></i> Новости</ingame:link>
</g:if>
<g:if test="${params.game.modules.contains(Game.GameModule.MAIL)}">
  <ingame:link class="item" controller="mailBox" action="index"><i class="mail outline icon"></i> Почта</ingame:link>
</g:if>
<g:if test="${params.game.modules.contains(Game.GameModule.RESOURCES)}">
  <ingame:link class="item" controller="gameResource" action="index"><i class="server icon"></i> Ресурсы</ingame:link>
</g:if>