package ru.srms.larp.platform.game.mail

// TODO встроить правила вывода писем
enum LetterType {
  INBOX('Входящие', { LetterContent it -> it?.letterFrom }),
  OUTGOING('Исходящие', { LetterContent it -> it?.letterTo }),
  DRAFT('Черновики', { LetterContent it -> it?.letterTo }),
  TRASH('Корзина', { LetterContent it -> it?.letterFrom ? it.letterFrom : it?.letterTo });

  String title
  Closure getAddress

  LetterType(String title, Closure getAddress) {
    this.getAddress = getAddress
    this.title = title
  }

  @Override
  String toString() {
    title
  }
}