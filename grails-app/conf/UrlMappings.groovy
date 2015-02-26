import org.springframework.security.access.AccessDeniedException
import org.springframework.security.acls.model.NotFoundException

class UrlMappings {

	static mappings = {
        // main page
        "/"(controller:'game')

        // spring security login controller
        "/login/$action?"{
            controller = 'login'
        }

        // game controller
        name game: "/play/$gameAlias/" (controller: 'game', action: 'play')

        // game administration
        "/play/$gameAlias/admin/$controller/$action?/$id?" {

        }

        // game character cabinet
        name playAs: "/play/$gameAlias/as/$charAlias"(controller: 'gameCharacter', action: 'playAs')

        // actions in character cabinet
        "/play/$gameAlias/as/$charAlias/$controller/$action?/$id?"{
            constraints {}
        }

        // status codes and errors
        "403"(controller: "errors", action: "error403")
        "404"(controller: "errors", action: "error404")
        "500"(controller: "errors", action: "error500")
        "500"(controller: "errors", action: "error403",
                exception: AccessDeniedException)
        "500"(controller: "errors", action: "error404",
                exception: NotFoundException)
	}
}
