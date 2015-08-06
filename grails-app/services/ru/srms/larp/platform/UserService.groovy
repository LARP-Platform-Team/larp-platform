package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.sec.SpringUser

@Transactional(readOnly = true)
class UserService {

  @Transactional
  @PreAuthorize("isFullyAuthenticated() and principal.username == #user.username")
  def save(SpringUser user) {
    user.save()
  }
}