package ru.srms.larp.platform.sec

import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.ui.RegisterController

class SpringUser {

  transient springSecurityService

  String username
  String password
  String email
  String name
  boolean enabled = true
  boolean accountExpired
  boolean accountLocked
  boolean passwordExpired

  static hasMany = [characters: GameCharacter]

  static transients = ['springSecurityService']

  static constraints = {
    username maxSize: 64, blank: false, unique: true, matches: RegisterController.USERNAME_REGEX
    email blank: false, unique: true, email: true
    password blank: false
    name maxSize: 64, blank: false, unique: true, matches: RegisterController.NAME_REGEX
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
    return name
  }
}
