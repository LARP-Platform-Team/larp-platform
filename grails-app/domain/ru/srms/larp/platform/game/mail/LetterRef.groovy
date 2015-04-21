package ru.srms.larp.platform.game.mail

import ru.srms.larp.platform.game.Game
import ru.srms.larp.platform.game.InGameStuff

class LetterRef implements InGameStuff {
  LetterType type
  Boolean deleted = false
  static belongsTo = [mailbox: MailBox, content: LetterContent]

  @Override
  Game extractGame() {
    return mailbox.game
  }
}
