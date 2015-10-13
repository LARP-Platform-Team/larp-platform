<!DOCTYPE html>
<html>
<head>
  <meta name="layout" content="main">
  <g:set var="title" value="Активация аккаунта"/>
  <title>${title}</title>
</head>

<body>
<content tag="content">
  <g:if test='${error}'>
    <ui:message type="error">${error}</ui:message>
  </g:if>
  <g:else>
    <ui:message type="success">
      Вы успешно активировали свой аккаунт! Теперь вы можете войти на сайт.
    </ui:message>
  </g:else>
</content>
</body>
</html>
