package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.GrantedAuthoritySid
import ru.srms.larp.platform.game.Titled
import ru.srms.larp.platform.game.news.NewsFeed
import ru.srms.larp.platform.game.roles.GameRole
import ru.srms.larp.platform.sec.permissions.AclConfigModel
import ru.srms.larp.platform.sec.permissions.GamePermission

/**
 * TODO 1 - figure out more DRY way to add ACL on domain object creation
 * TODO 2 - figure out what is objectIdentityRetrievalStrategy and how it works
 */
@Transactional
class GameAclService {

    def aclService
    def aclUtilService

    def createAcl(def object) {
        aclService.createAcl(
                aclUtilService.objectIdentityRetrievalStrategy.getObjectIdentity(object))
    }

    @PreAuthorize("hasPermission(#role.game, admin)")
    def setPermission(GameRole role, Long itemId, GamePermission permission, Boolean value) {
        if(value)
            aclUtilService.addPermission(NewsFeed.get(itemId), role.authority, permission.aclPermission)
        else
            aclUtilService.deletePermission(NewsFeed.get(itemId), role.authority, permission.aclPermission)
    }

    @Transactional(readOnly = true)
    List<AclConfigModel> getAclMatrix(GameRole role, List<Titled> objects) {
        objects.collect {
            AclConfigModel result = new AclConfigModel(id: it.id, title: it.extractTitle())

            aclUtilService.readAcl(it).entries.findAll {
                it.sid.equals(new GrantedAuthoritySid(role)) && GamePermission.existsFor(it.permission)
            }.each {
                result.permissions.add(GamePermission.valueFor(it.permission))
                print result.permissions
            }
            return result
        }
    }
}
