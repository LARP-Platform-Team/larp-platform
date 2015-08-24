<g:applyLayout name="base">
  <html>
  <head>
    <title><g:layoutTitle/></title>
    <g:layoutHead/>
  </head>

  <body>

  <div class="ui two columns stackable grid">

    <aside class="three wide column">
      <h1 сlass="ui small">Вам доступны</h1>
      <g:newsFeeds/>
      <g:availableMailboxes/>
      <g:availableResources/>
    </aside>

    <div class="ui thirteen wide column">
      <g:pageProperty name="page.content"/>
    </div>

  </div>
  </body>
  </html>
</g:applyLayout>