package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.GrantedAuthoritySid
import org.springframework.security.acls.model.Sid
import ru.srms.larp.platform.game.Titled
import ru.srms.larp.platform.game.news.NewsFeed
import ru.srms.larp.platform.game.roles.GameRole
import ru.srms.larp.platform.sec.permissions.AclConfigGroup
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

    @PreAuthorize("hasPermission(#role.game, admin)")
    @Transactional(readOnly = true)
    List<AclConfigGroup> getAclMatrix(GameRole role) {
        List<AclConfigGroup> result = []

        // TODO надо вывести все записи, участвующие в ACL
        result += new AclConfigGroup(title: "Новости", clazz: NewsFeed.class,
                models: getAclModels(new GrantedAuthoritySid(role), NewsFeed.findAllByGame(role.game)))

        return result
    }

    @Transactional(readOnly = true)
    private List<AclConfigModel> getAclModels(Sid roleSid, List<Titled> objects) {
        objects.collect {
            AclConfigModel result = new AclConfigModel(id: it.id, title: it.extractTitle())

            aclUtilService.readAcl(it).entries.findAll {
                it.sid.equals(roleSid) && GamePermission.existsFor(it.permission)
            }.each {
                result.permissions.add(GamePermission.valueFor(it.permission))
            }
            return result
        }
    }
}
