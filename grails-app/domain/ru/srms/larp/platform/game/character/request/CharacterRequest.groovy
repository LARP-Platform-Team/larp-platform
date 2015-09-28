package ru.srms.larp.platform.game.character.request

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.SpringUser

class CharacterRequest {

  RequestStatus status
  GameCharacter character
  String comment

  static belongsTo = [game: Game, user: SpringUser]
  static hasMany = [values: FormFieldValue]

  static constraints = {
    character nullable: true
    comment nullable: true, maxSize: 512
  }

  def beforeValidate() {
    values.each { it.validate() }
  }
}
