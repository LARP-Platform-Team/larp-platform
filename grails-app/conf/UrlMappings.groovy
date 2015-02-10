import org.springframework.security.access.AccessDeniedException
import org.springframework.security.acls.model.NotFoundException

class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {

            }
        }

        "/play/$alias/" {
            controller = 'game'
            action = 'play'
        }

        "/"(controller:'game')

        "403"(controller: "errors", action: "error403")
        "500"(controller: "errors", action: "error500")
        "500"(controller: "errors", action: "error403",
                exception: AccessDeniedException)
        "500"(controller: "errors", action: "error403",
                exception: NotFoundException)
	}
}
