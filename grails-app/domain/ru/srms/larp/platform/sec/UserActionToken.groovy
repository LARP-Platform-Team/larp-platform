package ru.srms.larp.platform.sec

class UserActionToken {

  String username
  String token
  Date dateCreated
  ActionType type

  static constraints = {
  }

  static mapping = {
    version false
  }

  enum ActionType {
    ACTIVATE, RESTORE_PASSWORD
  }
}
