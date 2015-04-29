<%@ page import="ru.srms.larp.platform.game.resources.GameResource" %>

<g:set var="game" value="${newsFeedInstance?.game ?: params.game}"/>
<g:hiddenField name="game.id" id="game" value="${game.id}"/>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'title', 'error')} required">
	<label for="title">Название<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${resourceInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: resourceInstance, field: 'measure', 'error')} required">
	<label for="measure">Единица измерения<span class="required-indicator">*</span>
	</label>
	<g:textField name="measure" required="" value="${resourceInstance?.measure ?: 'ед'}"/>
</div>
