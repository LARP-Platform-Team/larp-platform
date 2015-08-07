<g:applyLayout name="main">
  <html>
  <head>
    <title><g:layoutTitle/></title>
    <g:layoutHead/>
  </head>

  <body>
  <div class="ui two columns grid">

    <div class="ui thirteen wide column">
      <g:pageProperty name="page.content"/>
    </div>

    <div class="ui three wide right aligned column actions-panel">
      <div class="ui compact labeled icon vertical menu">
        <g:pageProperty name="page.actions"/>
      </div>
    </div>

  </div>
  </body>
  </html>
</g:applyLayout>