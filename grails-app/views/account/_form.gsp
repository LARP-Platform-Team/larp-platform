<div class="${hasErrors(bean: subject, field: 'email', 'error')} required field">
    <label for="email">E-mail<span class="required-indicator">*</span></label>
    <g:field type="text" name="email" required="" value="${subject?.email}"/>
</div>