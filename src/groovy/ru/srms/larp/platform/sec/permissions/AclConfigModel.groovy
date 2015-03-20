package ru.srms.larp.platform.sec.permissions

class AclConfigModel {
    Long id
    String title
    Set<GamePermission> permissions = []
}
