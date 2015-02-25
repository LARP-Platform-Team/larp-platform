<section>
    <h1>Доступные новостные ленты</h1>

    <ul class="property-list">
        <g:each in="${feeds}" var="feed">
            <li>
                <g:link resource="${feed}" action="show"
                        params="[gameAlias: feed.game.alias, charAlias: character.alias]">
                ${feed.title}</g:link>
            </li>
        </g:each>
    </ul>
</section>