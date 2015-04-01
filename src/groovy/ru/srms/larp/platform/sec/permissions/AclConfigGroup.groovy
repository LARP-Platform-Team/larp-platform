package ru.srms.larp.platform.sec.permissions

class AclConfigGroup {
    String title
    Class<?> clazz
    Set<AclConfigModel> models = []
}