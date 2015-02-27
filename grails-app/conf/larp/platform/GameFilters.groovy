package larp.platform

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.acl.AclUtilService
import org.springframework.security.acls.domain.BasePermission
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter

// TODO replace with Interceptor (http://grails.github.io/grails-doc/latest/guide/theWebLayer.html#interceptors)
@Deprecated
class GameFilters {

    AclUtilService aclUtilService
    SpringSecurityService springSecurityService
    def filters = {

        game(uri: '/play/**') {
            before = {
                params.game = Game.findByAlias(params.gameAlias)
                // TODO temp
                print 'game filter: ' + params.game
            }
        }

        gameAdmin(uri: '/play/*/admin/**') {
            before = {
                // TODO temp
                print 'admin filter'
                if(!aclUtilService.hasPermission(springSecurityService.authentication,
                        params.game ?: Game.findByAlias(params.gameAlias),
                        BasePermission.ADMINISTRATION)) {
                    // TODO find better way
                    redirect(controller: 'errors', action: 'error403')
                    return false
                }
                return true
            }
        }

        character(uri: '/play/*/as/**') {
            before = {
                params.character = GameCharacter.findByAlias(params.charAlias)
                // TODO temp
                print 'character filter: ' + params.character
            }
        }
    }
}
