<%@ page import="ru.srms.larp.platform.game.character.request.CharacterRequest; ru.srms.larp.platform.game.character.request.FieldType" %>
<g:set var="subject" value="${subject as CharacterRequest}"/>

<g:set var="sorted" value="${subject.values.sort { a, b ->
  a.field.sortOrder.compareTo(b.field.sortOrder)
}}"/>

<g:each in="${sorted}" var="value">
  <g:render template="fields/${value.field.type.toString()}" model="[field: value]"/>
</g:each>