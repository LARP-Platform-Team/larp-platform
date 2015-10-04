<%@ page import="ru.srms.larp.platform.game.resources.ResourceInstance" %>
<section class="ui pilled segment">
  <div class="ui blue ribbon label">Ресурсы</div>
  <ul class="property-list">
    <g:each in="${items}" var="item">
      <li>
        ${item.key.title}
        <g:set var="resList" value="${item.value as List<ResourceInstance>}"/>
        <ul>
          <g:each in="${resList}" var="res">
            <li>
              <ingame:link resource="${res}" action="show">${res.extractTitle()}</ingame:link>
              (${res.value}<g:if test="${res.type.measure}"> ${res.type.measure}</g:if>)
            </li>
          </g:each>
        </ul>
      </li>
    </g:each>
  </ul>
</section>