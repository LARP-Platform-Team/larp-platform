package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.GrantedAuthoritySid
import org.springframework.security.acls.model.AccessControlEntry
import ru.srms.larp.platform.game.roles.GameRole
import ru.srms.larp.platform.sec.permissions.AclConfigModel

/**
 * TODO 1 - figure out more DRY way to add ACL on domain object creation
 * TODO 2 - figure out what is objectIdentityRetrievalStrategy and how it works
 */
@Transactional
class GameAclService {

    def aclService
    def aclUtilService

    def createAcl(def object)
    {
        aclService.createAcl(
                aclUtilService.objectIdentityRetrievalStrategy.getObjectIdentity(object))
    }

    List<AclConfigModel> getAclMatrix(GameRole role, List objects) {
        objects.collect {
            AclConfigModel res = new AclConfigModel(id: it.id, title: it.title)
            List<AccessControlEntry> acl = aclUtilService.readAcl(it).entries
            List<AccessControlEntry> acl2 = acl.findAll {it.sid.equals(new GrantedAuthoritySid(role))}
            def acl3 = acl2.each {
                if(it.permission.equals(BasePermission.READ))
                    res.permissions += AclConfigModel.GamePermission.READ
                if(it.permission.equals(BasePermission.WRITE))
                    res.permissions += AclConfigModel.GamePermission.WRITE
                if(it.permission.equals(BasePermission.CREATE))
                    res.permissions += AclConfigModel.GamePermission.CREATE
                if(it.permission.equals(BasePermission.DELETE))
                    res.permissions += AclConfigModel.GamePermission.DELETE
            }
            return res
        }
    }
}
