package ru.srms.larp.platform.game.character.request

class FormFieldValue {

  String value
  static belongsTo = [field: RequestFormField, request: CharacterRequest]

  static constraints = {
    value maxSize: 1024, nullable: true, validator: { val, obj ->
      if(!obj.field.type.validate(obj))
        return ['wrong.value', obj.field.title]
    }
  }

  static mapping = {
    table 'character_request_form_field_value'
  }
}
