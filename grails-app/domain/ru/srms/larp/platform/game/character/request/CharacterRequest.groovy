package ru.srms.larp.platform.game.character.request

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.game.roles.GameRole
import ru.srms.larp.platform.sec.SpringUser

class CharacterRequest {

  RequestStatus status
  GameCharacter character
  String comment
  Date creationDate

  static belongsTo = [game: Game, user: SpringUser]
  static hasMany = [values: FormFieldValue, roles: GameRole]

  static constraints = {
    character nullable: true
    comment nullable: true, maxSize: 512
  }

  static mapping = {
    sort creationDate: 'asc'
    values cascade: 'all-delete-orphan'
  }

  def beforeValidate() {
    values?.each { it.validate() }
    if(id == null)
      creationDate = new Date()
  }
}
