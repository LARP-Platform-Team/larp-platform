package ru.srms.larp.platform.sec.permissions

import com.google.common.collect.ImmutableMap
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.model.Permission

enum GamePermission {

    READ(BasePermission.READ),
    WRITE(BasePermission.WRITE),
    CREATE(BasePermission.CREATE),
    DELETE(BasePermission.DELETE);

    Permission aclPermission

    GamePermission(Permission aclPermission) {
        this.aclPermission = aclPermission
    }

    private static final resolveMap = ImmutableMap.of(
            BasePermission.READ, READ,
            BasePermission.WRITE, WRITE,
            BasePermission.CREATE, CREATE,
            BasePermission.DELETE, DELETE);

    public static boolean existsFor(BasePermission permission) {
        return resolveMap.containsKey(permission)
    }

    public static GamePermission valueFor(BasePermission permission) {
        return resolveMap.get(permission)
    }

}
