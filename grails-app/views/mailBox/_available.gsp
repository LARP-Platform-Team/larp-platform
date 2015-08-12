<section class="ui pilled segment">
  <div class="ui blue ribbon label">Почта</div>
  <ul class="property-list">
    <g:each in="${items}" var="box">
      <li>
        <ingame:link resource="${box}" action="show">${box.address}</ingame:link>
      </li>
    </g:each>
  </ul>
</section>