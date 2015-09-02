package ru.srms.larp.platform.sec

class SpringRole {
		// TODO use this constaints as muck as possible
    public static final String ADMIN_ROLE = 'ROLE_ADMIN'
    public static final String GAME_MASTER_ROLE = 'ROLE_GM'
    public static final String ACL_EDITOR_ROLE = 'ROLE_ACL_CHANGE_DETAILS'

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
