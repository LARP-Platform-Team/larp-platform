<%@ page import="ru.srms.larp.platform.breadcrubms.Breadcrumb; ru.srms.larp.platform.game.Titled" %>
<div class="ui breadcrumb">
  <g:link uri="/" class="section">Главная</g:link>
  <g:if test="${breadcrumbs}">
    <g:set var="crumbs" value="${breadcrumbs as List<Breadcrumb>}"/>
    <g:each in="${crumbs}" var="crumb">
      <i class="right angle icon divider"></i>
      <g:if test="${crumb.url}">
        <g:link uri="${crumb.url}" class="section">${crumb.title}</g:link>
      </g:if>
      <g:else>
        <span>${crumb.title}</span>
      </g:else>
    </g:each>
  </g:if>
</div>
