package ru.srms.larp.platform.game.roles

import org.springframework.security.core.GrantedAuthority
import ru.srms.larp.platform.domain.Wrapped
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.Titled
import ru.srms.larp.platform.game.character.GameCharacter

class GameRole implements InGameStuff, Titled, GrantedAuthority, Wrapped<GameRole> {

  String title

  static belongsTo = [game: Game, parent: GameRole]
  static hasMany = [subRoles: GameRole]

  // TODO subRoles are not lazy to avoid N+1 problem. figure it out.
  static mapping = {
    subRoles sort: 'title', order: 'asc', lazy: false, cascade: 'all-delete-orphan'
    sort title: "asc"
  }

  static constraints = {
    title maxSize: 64
    parent nullable: true, validator: { val, obj ->
      if (!(val == null || val.game == obj.game))
        return 'gameRole.parent.wrongGame'
      if (val != null && obj.id != null && val.id == obj.id)
        return 'gameRole.parent.selfParent'
    }
  }

  static transients = ['wrapper']

  Set<GameCharacter> getCharacters() {
    CharacterRole.findAllByRole(this).collect({ it.character }).toSet()
  }

  def beforeDelete() {
    CharacterRole.removeAll(this)
    deleteWrapper()
  }

  @Override
  public String toString() {
    return title;
  }

  @Override
  String getAuthority() {
    return "ROLE_" + id + "_in_" + game.alias
  }

  @Override
  Game extractGame() {
    return game
  }

  @Override
  String extractTitle() {
    return title
  }
}
