package ru.srms.larp.platform.game.character.request

import ru.srms.larp.platform.EntityWrapper
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

class RequestFormField implements InGameStuff {

  FieldType type
  String title
  String hint
  Boolean required = false
  Integer sortOrder
  String data

  static belongsTo = [parent: EntityWrapper]
  static hasMany = [values: FormFieldValue]

  static constraints = {
    title maxSize: 32
    hint nullable: true, maxSize: 128
    data nullable: true, maxSize: 512
  }

  def beforeValidate() {
    if(!FieldType.SELECT.equals(type))
      data = null
  }

  static mapping = {
    table 'character_request_form_field'
    parent cascade: 'save-update'
    sort sortOrder: 'asc'
  }

  @Override
  Game extractGame() {
    parent.extractGame()
  }
}
