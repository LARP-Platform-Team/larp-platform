<%@ page import="ru.srms.larp.platform.game.Titled" %>
<div class="ui breadcrumb">
  <g:link uri="/" class="section">Главная</g:link>

  <g:if test="${params.game}">
    <i class="right angle icon divider"></i>
    <g:link mapping="game" class="section active" params="[gameAlias: params.gameAlias]">
      ${(params.game as Titled).extractTitle()}
    </g:link>
  </g:if>
</div>