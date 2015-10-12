<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="${g.message(code: 'spring.security.ui.login.title')}"/>
  <title>${title}</title>
</head>

<body>
<content tag="content">

  <form action='${postUrl}' method='POST' class="ui form" name="loginForm" autocomplete='off'>

    <div class="inline required field">
      <label for="username"><g:message code='spring.security.ui.login.username'/></label>
      <input name="j_username" required="required" id="username" size="20"/>
    </div>

    <div class="inline required field">
      <label for="password"><g:message code='spring.security.ui.login.password'/></label>
      <input type="password" required="required" name="j_password" id="password" size="20"/>
    </div>

    <div class="inline field">
      <input type="checkbox" class="ui checkbox" name="${rememberMeParameter}" id="remember_me"
             checked="checked"/>
      <label for='remember_me'><g:message code='spring.security.ui.login.rememberme'/></label>
    </div>

    <ui:submit name="enter" icon="privacy">
      <g:message code='spring.security.ui.login.login'/></ui:submit>
    <g:link class="ui labeled icon button" controller='register' action='restorePassword'>
      <i class="refresh icon"></i><g:message code='spring.security.ui.login.forgotPassword'/></g:link>
    <g:link class="ui labeled icon button" controller='register' action="index">
      <i class="add user icon"></i><g:message code='spring.security.ui.login.register'/></g:link>
  </form>

  <script>
    $(document).ready(function () {
      $('#username').focus();
    });
  </script>
</content>

</body>
</html>
