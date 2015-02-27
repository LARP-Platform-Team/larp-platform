package larp.platform

class GameTagLib {
//    static defaultEncodeAs = [taglib: 'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    /**
     * Extends general linking to controllers, actions etc.<br/>
     * GameAlias and CharAlias are extracted from params by default<br/>
     *
     * @attr controller The name of the controller to use in the link, if not specified the current controller will be linked
     * @attr action The name of the action to use in the link, if not specified the default action will be linked
     * @attr uri relative URI
     * @attr url A map containing the action,controller,id etc.
     * @attr base Sets the prefix to be added to the link target address, typically an absolute server URL. This overrides the behaviour of the absolute property, if both are specified.
     * @attr absolute If set to "true" will prefix the link target address with the value of the grails.serverURL property from Config, or http://localhost:&lt;port&gt; if no value in Config and not running in production.
     * @attr id The id to use in the link
     * @attr fragment The link fragment (often called anchor tag) to use
     * @attr params A map containing URL query parameters
     * @attr mapping The named URL mapping to use to rewrite the link
     * @attr event Webflow _eventId parameter
     * @attr elementId DOM element id
     * @attr gameAlias by default is extracted from params
     * @attr charAlias by default is extracted from params
     */
    def gameLink = { attrs, body ->
        if (!attrs.params)
            attrs.params = [:]

        if (!attrs.params.gameAlias)
            attrs.params.gameAlias = attrs.gameAlias ?: params.gameAlias

        def charAlias = attrs.charAlias ?: params.charAlias
        if (!attrs.params.charAlias && charAlias)
            attrs.params.charAlias = charAlias

        out << g.link(attrs, body)
    }
}
