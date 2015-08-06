<div class="fieldcontain ${hasErrors(bean: changePassword, field: 'oldPassword', 'error')} required">
  <label for="oldPassword">Старый пароль</label>
  <g:passwordField name="oldPassword" value=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: changePassword, field: 'newPassword', 'error')} required">
  <label for="newPassword">Новый пароль</label>
  <g:passwordField name="newPassword" value=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: changePassword, field: 'confirmPassword', 'error')} required">
  <label for="confirmPassword">Повторите новый пароль</label>
  <g:passwordField name="confirmPassword" value=""/>
</div>