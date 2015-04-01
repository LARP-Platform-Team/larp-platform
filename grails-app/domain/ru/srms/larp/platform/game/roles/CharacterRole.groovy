package ru.srms.larp.platform.game.roles

import org.apache.commons.lang.builder.HashCodeBuilder
import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff
import ru.srms.larp.platform.game.character.GameCharacter

class CharacterRole implements InGameStuff, Serializable {

    GameCharacter character
    GameRole role

    static mapping = {
        id composite: ['character', 'role']
        version false
        table 'game_character_roles'
    }

    static constraints = {
        character validator: {val, obj -> val.game == obj.role.game}
    }

    static CharacterRole get(GameCharacter character, GameRole role) {
        get(id(character, role))
    }

    static CharacterRole create(GameCharacter character, GameRole role, boolean flush = false) {
        new CharacterRole(character: character, role: role).save(flush: flush, insert: true)
    }

    static void remove(GameCharacter character, GameRole role, boolean flush = false) {
        def instance = get(id(character, role))
        if(instance)
            instance.delete(flush: flush)
    }

    static CharacterRole id(GameCharacter character, GameRole role) {
        new CharacterRole(character: character, role: role)
    }

    static void removeAll(GameCharacter character) {
        executeUpdate 'DELETE FROM CharacterRole WHERE character=:character', [character: character]
    }

    static void removeAll(GameRole role) {
        executeUpdate 'DELETE FROM CharacterRole WHERE role=:role', [role: role]
    }


    @Override
    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        CharacterRole that = (CharacterRole) o
        return character.id != that.character.id && role.id != that.role.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        builder.append(character?.id)
        builder.append(role?.id)
        builder.toHashCode()
    }

    @Override
    Game extractGame() {
        return role ? role.game : character?.game;
    }
}

