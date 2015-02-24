import org.springframework.security.access.AccessDeniedException
import org.springframework.security.acls.model.NotFoundException

class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {

            }
        }

        name game: "/play/$alias/" (controller: 'game', action: 'play')
        name playAs: "/play/$gameAlias/as/$charAlias"(controller: 'gameCharacter', action: 'playAs')

        "/"(controller:'game')

        "403"(controller: "errors", action: "error403")
        "404"(controller: "errors", action: "error404")
        "500"(controller: "errors", action: "error500")
        "500"(controller: "errors", action: "error403",
                exception: AccessDeniedException)
        "500"(controller: "errors", action: "error404",
                exception: NotFoundException)
	}
}
