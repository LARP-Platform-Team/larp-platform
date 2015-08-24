<g:applyLayout name="mainWithActions">
  <html>
  <head>
    <title><g:layoutTitle/></title>
    <g:layoutHead/>
  </head>

  <body>
  <content tag="content">
    <g:pageProperty name="page.content"/>
    <aside class="content">
      <g:newsFeeds/>
      <g:availableMailboxes/>
      <g:availableResources/>
    </aside>
  </content>
  </body>
  </html>
</g:applyLayout>