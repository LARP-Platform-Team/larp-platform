package ru.srms.larp.platform.sec.permissions

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.acl.AclService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.security.acls.AclPermissionEvaluator
import org.springframework.security.acls.domain.*
import org.springframework.security.acls.model.*
import org.springframework.security.core.Authentication
import org.springframework.web.context.request.RequestContextHolder
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.SpringRole
import ru.srms.larp.platform.sec.SpringUser
import ru.srms.larp.platform.sec.SpringUserSpringRole

/**
 *
 * <p>Created 12.03.15</p>
 * @author kblokhin
 */
class GamePermissionsEvaluator extends AclPermissionEvaluator {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final AclService aclService;
    private final SpringSecurityService springSecurityService;

    private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();
    private ObjectIdentityGenerator objectIdentityGenerator = new ObjectIdentityRetrievalStrategyImpl();
    private SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();
    private PermissionFactory permissionFactory = new DefaultPermissionFactory();

    GamePermissionsEvaluator(AclService aclService, SpringSecurityService springSecurityService) {
        super(aclService)
        this.springSecurityService = springSecurityService;
        this.aclService = aclService
    }

    @Override
    boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if (!springSecurityService.isLoggedIn() || targetDomainObject == null)
            return false

        // for users with ROLE_ADMIN all permissions are granted
        def user = springSecurityService.currentUser as SpringUser
        if (SpringUserSpringRole.findBySpringUserAndSpringRole(
                user, SpringRole.findByAuthority('ROLE_ADMIN')))
                return true;

        GameCharacter character = RequestContextHolder.requestAttributes?.params?.character

        def basePermission = super.hasPermission(authentication, targetDomainObject, permission)
        if(basePermission)
            return true

        if(!character)
            return false

        return hasCharacterPermission(character,
                                this.objectIdentityRetrievalStrategy.getObjectIdentity(targetDomainObject),
                                permission)
    }

    private boolean hasCharacterPermission(GameCharacter character, ObjectIdentity oid, Object permission) {
        print "check character $character.name permissions!"
        print "character roles: $character.roles"

        List<Sid> sids = character.getRoles().collect {new GrantedAuthoritySid(it)}
        if(sids.isEmpty())
            return false

        List requiredPermission = this.resolvePermission(permission);
        boolean debug = this.logger.isDebugEnabled();
        if (debug) {
            this.logger.debug("Checking permission \'" + permission + "\' for object \'" + oid + "\'");
        }

        try {
            Acl nfe = this.aclService.readAclById(oid, sids);
            if (nfe.isGranted(requiredPermission, sids, false)) {
                if (debug) {
                    this.logger.debug("Access is granted");
                }

                return true;
            }

            if (debug) {
                this.logger.debug("Returning false - ACLs returned, but insufficient permissions for this principal");
            }
        } catch (NotFoundException var8) {
            if (debug) {
                this.logger.debug("Returning false - no ACLs apply for this principal");
            }
        }

        return false;
    }

    @Override
    boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        //        ObjectIdentity objectIdentity = this.objectIdentityGenerator.createObjectIdentity(targetId, targetType);
//        return checkPermission(authentication, objectIdentity, permission);
        print "my eva 2!"
        return super.hasPermission(authentication, targetId, targetType, permission)
    }

    List<Permission> resolvePermission(Object permission) {
        if (permission instanceof Integer)
            return [this.permissionFactory.buildFromMask(permission.intValue())]

        if (permission instanceof Permission)
            return [permission]

        if (permission instanceof Permission[])
            return permission as List<Permission>

        if (permission instanceof String) {
            Permission p;
            try {
                p = this.permissionFactory.buildFromName(permission);
            } catch (IllegalArgumentException ignored) {
                p = this.permissionFactory.buildFromName(permission.toUpperCase());
            }

            if (p != null) {
                return [p];
            }
        }

        throw new IllegalArgumentException("Unsupported permission: " + permission);
    }

    public void setObjectIdentityRetrievalStrategy(ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy) {
        this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
    }

    public void setObjectIdentityGenerator(ObjectIdentityGenerator objectIdentityGenerator) {
        this.objectIdentityGenerator = objectIdentityGenerator;
    }

    public void setSidRetrievalStrategy(SidRetrievalStrategy sidRetrievalStrategy) {
        this.sidRetrievalStrategy = sidRetrievalStrategy;
    }

    public void setPermissionFactory(PermissionFactory permissionFactory) {
        this.permissionFactory = permissionFactory;
    }
}

