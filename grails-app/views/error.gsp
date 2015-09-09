<!DOCTYPE html>
<html>
<head>
  <title><g:if env="development">Grails Runtime Exception</g:if><g:else>Произошла ошибка :(</g:else></title>
  <meta name="layout" content="main">
</head>

<body>
<content tag="content">
  <g:if env="development">
    <g:renderException exception="${exception}"/>
  </g:if>
  <g:else>
    <ui:message type="error">К сожалению, что-то пошло не так. Пожалуйста, свяжитесь с нами и расскажите, как так вышло :)</ui:message>
  </g:else>
</content>
</body>
</html>
