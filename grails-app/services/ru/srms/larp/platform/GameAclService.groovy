package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.acls.domain.GrantedAuthoritySid
import ru.srms.larp.platform.game.TitledIdentifiable
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

    def createAcl(def object)
    {
        aclService.createAcl(
                aclUtilService.objectIdentityRetrievalStrategy.getObjectIdentity(object))
    }

    List<AclConfigModel> getAclMatrix(GameRole role, List<TitledIdentifiable> objects) {
        objects.collect {
            AclConfigModel res = new AclConfigModel(id: it.id, title: it.title)

            aclUtilService.readAcl(it).entries
                    .findAll {
                def sid = it.sid.equals(new GrantedAuthoritySid(role))

                def exists = GamePermission.existsFor(it.permission)
                sid && exists
            }
                    .each {
                res.permissions.add(GamePermission.valueFor(it.permission))
                print res.permissions
            }
                    

//            List<AccessControlEntry> acl = aclUtilService.readAcl(it).entries
//            List<AccessControlEntry> acl2 = acl.findAll {it.sid.equals(new GrantedAuthoritySid(role)) && GamePermission.existsFor(it.permission)}
//            def acl3 = acl2.each {
//                if(GamePermission.existsFor(it))
//                    res.permissions += GamePermission.valueFor(it)
//                if(it.permission.equals(BasePermission.READ))
//                    res.permissions += AclConfigModel.GamePermission.READ
//                if(it.permission.equals(BasePermission.WRITE))
//                    res.permissions += AclConfigModel.GamePermission.WRITE
//                if(it.permission.equals(BasePermission.CREATE))
//                    res.permissions += AclConfigModel.GamePermission.CREATE
//                if(it.permission.equals(BasePermission.DELETE))
//                    res.permissions += AclConfigModel.GamePermission.DELETE
//            }
            return res
        }
    }
}
