package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.sec.SpringUser
import ru.srms.larp.platform.sec.ui.RegisterCommand

@Transactional(readOnly = true)
class UserService {

  @Transactional
  @PreAuthorize("isFullyAuthenticated() and principal.username == #user.username")
  def save(SpringUser user) {
    user.save()
  }

  @Transactional
  def register(RegisterCommand command) {
    def user = new SpringUser(
        username: command.username,
        password: command.password,
        email: command.email,
        name: command.name,
        enabled: true,
        accountExpired: false,
        accountLocked: false,
        passwordExpired: false
    )
    if(!user.validate())
      throw new Exception("Wrong register parameters")

    user.save()


  }
}