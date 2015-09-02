package ru.srms.larp.platform.game

import ru.srms.larp.platform.game.character.GameCharacter
import ru.srms.larp.platform.sec.SpringUser

class Game implements Titled {

  String title
  String alias
  String overview
  static hasMany = [masters: SpringUser, characters: GameCharacter, modules: GameModule]

  static constraints = {
    title maxSize: 32, unique: true
    alias maxSize: 32, matches: /^[A-Za-z0-9\-]+$/, unique: true
    overview maxSize: 9999
  }

  static mapping = {
    masters joinTable: "game_masters"
  }

  @Override
  String toString() {
    return title
  }

  @Override
  String extractTitle() {
    return title
  }

  static enum GameModule {
    NEWS('Новости'),
    MAIL('Почта'),
    RESOURCES('Ресурсы и банки');

    String title

    GameModule(String title) {
      this.title = title
    }
  }
}
