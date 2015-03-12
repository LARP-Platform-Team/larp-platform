package ru.srms.larp.platform.sec.permissions

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.acl.AclService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.security.acls.AclPermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.web.context.request.RequestContextHolder
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.SpringRole
import ru.srms.larp.platform.sec.SpringUser

/**
 *
 * <p>Created 12.03.15</p>
 * @author kblokhin
 */
class GamePermissionsEvaluator extends AclPermissionEvaluator {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final AclService aclService;
    private final SpringSecurityService springSecurityService;

    GamePermissionsEvaluator(AclService aclService, SpringSecurityService springSecurityService) {
        super(aclService)
        this.springSecurityService = springSecurityService;
        this.aclService = aclService
    }

    @Override
    boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        print ""
        print "my eva!"


        if(springSecurityService.isLoggedIn())
        {
            print "is logged!"
            def user = springSecurityService.currentUser as SpringUser
            def authorities = user.getAuthorities()
            def admin = SpringRole.findByAuthority('ROLE_ADMIN')
            def gm = SpringRole.findByAuthority('ROLE_GM')
            print "gm: "+ authorities.contains(gm)
            print "admin: "+ authorities.contains(admin)
            if(authorities.contains(admin))
                return true;
        }

        GameCharacter character = RequestContextHolder.requestAttributes?.params?.character
        if(character)
        {
            print "is char $character.name!"
            print character.roles
        }
        return super.hasPermission(authentication, targetDomainObject, permission)
    }

    @Override
    boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        print "my eva 2!"
        return super.hasPermission(authentication, targetId, targetType, permission)
    }
}
