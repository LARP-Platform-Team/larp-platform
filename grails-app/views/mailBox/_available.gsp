<section>
  <h1>Доступные почтовые ящики</h1>
  <ul class="property-list">
    <g:each in="${items}" var="box">
      <li>
        <ingame:link resource="${box}" action="show">${box.address}</ingame:link>
      </li>
    </g:each>
  </ul>
</section>