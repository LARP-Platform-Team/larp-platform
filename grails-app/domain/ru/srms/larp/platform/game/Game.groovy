package ru.srms.larp.platform.game

import ru.srms.larp.platform.domain.Wrapped
import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.SpringUser

class Game implements Titled, Wrapped<Game>, InGameStuff {

  String title
  String alias
  String preview
  String overview
  Boolean active = false

  transient int previewPureLength
  static hasMany = [
          masters          : SpringUser,
          characters       : GameCharacter,
          modules          : GameModule
  ]

  static transients = ['previewPureLength', 'wrapper']

  static constraints = {
    title maxSize: 32, unique: true
    alias maxSize: 32, matches: /^[A-Za-z0-9\-]+$/, unique: true
    overview maxSize: 9999
    preview maxSize: 1000
    previewPureLength bindable: true, max: 512
  }

  static mapping = {
    masters joinTable: "game_masters"
  }

  def beforeDelete() {
    deleteWrapper()
  }

  @Override
  String toString() {
    return title
  }

  @Override
  String extractTitle() {
    return title
  }

  @Override
  Game extractGame() {
    return this
  }

  static enum GameModule {
    NEWS('Новости'),
    MAIL('Почта'),
    REQUEST_FORM('Анкета игрока'),
    RESOURCES('Ресурсы и банки');

    String title

    GameModule(String title) {
      this.title = title
    }
  }
}
