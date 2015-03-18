package ru.srms.larp.platform.sec.permissions
/**
 *
 * <p>Created 18.03.15</p>
 * @author kblokhin
 */
class AclConfigModel {

    public static enum GamePermission {
        READ, WRITE, CREATE, DELETE
    }

    Long id
    String title
    Set<GamePermission> permissions = []
}
