package ru.srms.larp.platform.sec

import ru.srms.larp.platform.game.character.GameCharacter

class SpringUser {

	transient springSecurityService

	String username
	String password
	String email
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

    static hasMany = [characters: GameCharacter]

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		email blank: false, unique: true, email: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<SpringRole> getAuthorities() {
		SpringUserSpringRole.findAllBySpringUser(this).collect { it.springRole }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

    @Override
    String toString() {
        return username
    }
}
