package ru.srms.larp.platform.game.character

import org.springframework.security.core.GrantedAuthority
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled
import ru.srms.larp.platform.game.roles.CharacterRole
import ru.srms.larp.platform.game.roles.GameRole
import ru.srms.larp.platform.sec.SpringUser

class GameCharacter implements InGameStuff, Titled, GrantedAuthority {

  String name
  String alias
  Boolean isDead = false
  SpringUser player

  static belongsTo = [game: Game]

  static constraints = {
    // character names and aliases must be unique in the game context
    name maxSize: 64, unique: 'game'
    alias maxSize: 32, matches: /^[A-Za-z0-9\-]+$/, unique: 'game'
    player nullable: true
  }

  Set<GameRole> getRoles() {
    CharacterRole.findAllByCharacter(this).collect({ it.role }).toSet()
  }

  def beforeDelete() {
    CharacterRole.removeAll(this)
  }

  @Override
  String toString() {
    name
  }

  @Override
  Game extractGame() {
    return game
  }

  @Override
  String extractTitle() {
    return name
  }

  @Override
  String getAuthority() {
    return "ROLE_CHARACTER_" + id + "_in_" + game.alias
  }
}
