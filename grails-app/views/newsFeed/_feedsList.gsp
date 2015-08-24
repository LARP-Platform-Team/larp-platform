<section class="ui pilled segment">
    <div class="ui blue ribbon label">Новости</div>
    <ul class="property-list">
        <g:each in="${feeds}" var="feed">
            <li>
                <ingame:link resource="${feed}" action="show">${feed.title}</ingame:link>
            </li>
        </g:each>
    </ul>
</section>