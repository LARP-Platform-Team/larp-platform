<%@ page import="ru.srms.larp.platform.game.character.request.RequestFormField" %>
<g:set var="fields" value="${fields as List<RequestFormField>}"/>

<div class="ui relaxed divided list">
  <g:each in="${fields}" var="field">
    <div class="item">
      <div class="header">${field.title}</div>

      <div class="content">
        <g:render template="output/${field.type.toString()}"
                  model="[value: values.get(field.id)?.value]"/>
      </div>
    </div>
  </g:each>
</div>