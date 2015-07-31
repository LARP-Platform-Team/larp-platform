<%@ page import="ru.srms.larp.platform.game.character.GameCharacter; ru.srms.larp.platform.game.mail.MailBox" %>

<div class="fieldcontain ${hasErrors(bean: subject, field: 'title', 'error')} required">
    <label for="title">Название<span class="required-indicator">*</span></label>
    <g:field type="text" name="title" required="" value="${subject?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subject, field: 'value', 'error')} required">
    <label for="identifier">Количество ресурса</label>
    <g:field type="text" name="value" required="" value="${subject?.value}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subject, field: 'identifier', 'error')} required">
    <label for="identifier">Идентификатор<span class="required-indicator">*</span></label>
    <g:field type="text" name="identifier" required="" value="${subject?.identifier}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subject, field: 'origin', 'error')}">
    <label for="origin.id">Источник</label>
    <g:select name="origin.id" from="${subject.type.origins}" value="${subject.origin?.id}"
              optionKey="id" optionValue="title" noSelection="${['null': 'нет']}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subject, field: 'owner', 'error')}">
    <label for="owner.id">Владелец</label>
    <g:select name="owner.id" from="${GameCharacter.findAllByGame(params.game)}"
              value="${subject.owner?.id}" optionKey="id" noSelection="${['null': 'нет']}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: subject, field: 'transferable', 'error')}">
    <label for="identifier">Можно переводить средства?</label>
    <g:checkBox name="transferable" value="${subject?.transferable}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: subject, field: 'ownerEditable', 'error')}">
    <label for="identifier">Игрок может менять значение?</label>
    <g:checkBox name="ownerEditable" value="${subject?.ownerEditable}"/>
</div>

<g:hiddenField name="type.id" id="type" value="${subject.type.id}"/>
<g:hiddenField name="game.id" id="game" value="${params.game.id}"/>