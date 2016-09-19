<%@ page import="ru.srms.larp.platform.game.character.request.FormFieldValue; ru.srms.larp.platform.game.roles.GameRole; ru.srms.larp.platform.game.character.request.RequestFormField; ru.srms.larp.platform.game.character.request.CharacterRequest" %>
<g:set var="subject" value="${subject as CharacterRequest}"/>
<g:set var="gameFields" value="${gameFields as List<RequestFormField>}"/>
<g:set var="roles" value="${roles as Map<GameRole, List<RequestFormField>>}"/>
<g:set var="values" value="${values as Map<Long, FormFieldValue>}"/>

<g:each in="${gameFields}" var="field">
  <g:render template="inputFields/${field.type.toString()}"
            model="[field: values.get(field.id) ?: new FormFieldValue(field: field)]"/>
</g:each>

<div class="${hasErrors(bean: subject, field: 'roles', 'error')} field">
  <label for="roles">Роли:</label>
  <g:select class="ui dropdown" from="${roles?.keySet()}" multiple="true" optionKey="id"
            name="roles" id="character_request_roles" value="${subject.roles}"/>
</div>

<div class="ui stackable grid">
  <g:each in="${roles}" var="roleFields">
    <g:set var="isShown" value="${subject.roles && subject.roles.contains(roleFields.key)}"/>
    <div class="ui eight wide column ${isShown ? '' : 'larp-hidden'}"
         id="role_${roleFields.key.id}_fields">
      <div class="ui pilled segment">
        <h4>Поля для роли ${roleFields.key.title}</h4>

        <g:each in="${roleFields.value}" var="field">
          <g:render template="inputFields/${field.type.toString()}"
                    model="[field: values.get(field.id) ?: new FormFieldValue(field: field),
                            isHidden: !isShown]"/>
        </g:each>
      </div>
    </div>
</g:each>
</div>

<div class="ui clearing hidden divider"></div>
