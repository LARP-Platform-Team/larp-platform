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

class GamePermissionsEvaluator extends AclPermissionEvaluator {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final AclService aclService;
    private final SpringSecurityService springSecurityService;

    private ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy = new ObjectIdentityRetrievalStrategyImpl();
    private ObjectIdentityGenerator objectIdentityGenerator = new ObjectIdentityRetrievalStrategyImpl();
    // TODO maybe create custom sid retrieval strategy
    private SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();
    private PermissionFactory permissionFactory = new DefaultPermissionFactory();

    GamePermissionsEvaluator(AclService aclService, SpringSecurityService springSecurityService) {
        super(aclService)
        this.springSecurityService = springSecurityService;
        this.aclService = aclService
    }

    @Override
    boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if(super.hasPermission(authentication, targetDomainObject, permission)) return true
        if (targetDomainObject == null) return false

        def oid = objectIdentityRetrievalStrategy.getObjectIdentity(targetDomainObject)
        return checkPermission(authentication, oid, permission);
    }

    @Override
    boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if(super.hasPermission(authentication, targetId, targetType, permission)) return true

        ObjectIdentity oid = objectIdentityGenerator.createObjectIdentity(targetId, targetType);
        return checkPermission(authentication, oid, permission);
    }

    private boolean checkPermission(Authentication authentication, ObjectIdentity oid, Object permission) {
        // everything is permitted for admin
        if(authentication.authorities.any { SpringRole.ADMIN_ROLE.equals(it.authority) })
            return true

        // check character permissions
        GameCharacter character = RequestContextHolder.requestAttributes?.params?.character
        if(!character) return false

        return checkCharacterPermission(character, oid, permission)
    }

    private boolean checkCharacterPermission(GameCharacter character, ObjectIdentity oid, Object permission) {
        List<Sid> sids = character.getRoles().collect {new GrantedAuthoritySid(it)}
        if(sids.isEmpty()) return false

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
        } catch (NotFoundException e) {
            if (debug) {
                this.logger.debug("Returning false - no ACLs apply for this principal");
            }
        }

        return false;
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
        super.setObjectIdentityRetrievalStrategy(objectIdentityRetrievalStrategy)
        this.objectIdentityRetrievalStrategy = objectIdentityRetrievalStrategy;
    }

    public void setObjectIdentityGenerator(ObjectIdentityGenerator objectIdentityGenerator) {
        super.setObjectIdentityGenerator(objectIdentityGenerator)
        this.objectIdentityGenerator = objectIdentityGenerator;
    }

    public void setSidRetrievalStrategy(SidRetrievalStrategy sidRetrievalStrategy) {
        super.setSidRetrievalStrategy(sidRetrievalStrategy)
        this.sidRetrievalStrategy = sidRetrievalStrategy;
    }

    public void setPermissionFactory(PermissionFactory permissionFactory) {
        super.setPermissionFactory(permissionFactory)
        this.permissionFactory = permissionFactory;
    }
}

