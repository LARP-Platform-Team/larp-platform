<%@ page import="ru.srms.larp.platform.game.roles.GameRole" %>

<div class="fieldcontain ${hasErrors(bean: gameRoleInstance, field: 'title', 'error')} required">
    <label for="title">
        <g:message code="property.title.label" default="Title"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="title" required="" value="${gameRoleInstance?.title}"/>

</div>

<g:if test="${gameRoleInstance.parent}">
    <div class="fieldcontain ${hasErrors(bean: gameRoleInstance, field: 'parent', 'error')} ">
        <label for="parent">
            <g:message code="gameRole.parent.label" default="Parent"/>
        </label>
        <span>${gameRoleInstance.parent.title}</span>
        <g:hiddenField  id="parent" name="parent.id" value="${gameRoleInstance?.parent?.id}"/>
    </div>
</g:if>

<div class="fieldcontain ${hasErrors(bean: gameRoleInstance, field: 'game', 'error')} required">
    <label for="game">
        <g:message code="game.label"/>
        <span class="required-indicator">*</span>
    </label>
    <span>${gameRoleInstance.game.title}</span>
    <g:hiddenField name="game.id" id="game" value="${gameRoleInstance.game.id}"/>
</div>



