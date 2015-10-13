package ru.srms.larp.platform

import grails.transaction.Transactional
import org.springframework.security.access.prepost.PreAuthorize
import ru.srms.larp.platform.sec.SpringUser
import ru.srms.larp.platform.sec.UserActionToken
import ru.srms.larp.platform.sec.ui.ResetPasswordCommand

@Transactional(readOnly = true)
class UserService {

  @Transactional
  @PreAuthorize("isFullyAuthenticated() and principal.username == #user.username")
  def save(SpringUser user) {
    user.save()
  }

  @Transactional
  def register(SpringUser user, boolean activationRequired) {
    user.save()

    if (activationRequired) {
      def action = new UserActionToken(username: user.username,
          type: UserActionToken.ActionType.ACTIVATE,
          token: generateHash())
      if (!action.save())
        throw new Exception("Can't save activate action")

      return action.token
    }
  }

  @Transactional
  def activate(SpringUser user, UserActionToken action) {
    user.enabled = true
    user.save()
    action.delete()
  }

  @Transactional
  def createPasswordRestoreToken(String username) {
    def prevToken = UserActionToken
        .findByUsernameAndType(username, UserActionToken.ActionType.RESTORE_PASSWORD)
    prevToken?.delete(flush: true)

    def action = new UserActionToken(username: username,
        type: UserActionToken.ActionType.RESTORE_PASSWORD,
        token: generateHash())
    if (!action.save())
      throw new Exception("Can't save password restore action")

    return action.token
  }

  @Transactional
  def setNewPassword(ResetPasswordCommand command) {
    def action = UserActionToken.findByToken(command.token)
    SpringUser user = SpringUser.findByUsername(action.username)
    user.password = command.password
    if (!user.save())
      throw new Exception("Unable to save user")

    action.delete()
  }

  private String generateHash() {
    UUID.randomUUID().toString().encodeAsMD5()
  }
}