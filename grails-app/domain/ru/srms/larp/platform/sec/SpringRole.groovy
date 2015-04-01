package ru.srms.larp.platform.sec

class SpringRole {

    public static final String ADMIN_ROLE = 'ROLE_ADMIN'

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
