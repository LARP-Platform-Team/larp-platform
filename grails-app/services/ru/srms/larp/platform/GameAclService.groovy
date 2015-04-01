package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.GrantedAuthoritySid
import ru.srms.larp.platform.game.Titled
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
    boolean setPermission(GameRole role, Class<?> clazz, Long itemId, GamePermission permission) {
        def find = aclUtilService.readAcl(clazz, itemId).entries.find {
            it.sid.equals(new GrantedAuthoritySid(role)) && it.permission.equals(permission.aclPermission)
        }
        boolean exists = find?.granting ?: false

        if(!exists)
            aclUtilService.addPermission(clazz, itemId, role.authority, permission.aclPermission);
        else
            aclUtilService.deletePermission(clazz, itemId, role.authority, permission.aclPermission);

        return !exists
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasPermission(#role.game, admin)")
    List<AclConfigModel> getAclMatrix(GameRole role, List<Titled> objects) {
        objects.collect {
            AclConfigModel result = new AclConfigModel(id: it.id, title: it.extractTitle())

            aclUtilService.readAcl(it).entries.findAll {
                it.sid.equals(new GrantedAuthoritySid(role)) && GamePermission.existsFor(it.permission)
            }.each {
                result.permissions.add(GamePermission.valueFor(it.permission))
            }
            return result
        }
    }
}
