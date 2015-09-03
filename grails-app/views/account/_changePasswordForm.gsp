<div class="${hasErrors(bean: changePassword, field: 'oldPassword', 'error')} required field">
  <label for="oldPassword">Старый пароль</label>
  <g:passwordField name="oldPassword" value=""/>
</div>

<div class="${hasErrors(bean: changePassword, field: 'newPassword', 'error')} required field">
  <label for="newPassword">Новый пароль</label>
  <g:passwordField name="newPassword" value=""/>
</div>

<div class="${hasErrors(bean: changePassword, field: 'confirmPassword', 'error')} required field">
  <label for="confirmPassword">Повторите новый пароль</label>
  <g:passwordField name="confirmPassword" value=""/>
</div>