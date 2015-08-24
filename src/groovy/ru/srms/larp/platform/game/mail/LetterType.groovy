package ru.srms.larp.platform.game.mail

// TODO встроить правила вывода писем
enum LetterType {
  INBOX('Входящие', 'sign in', { LetterContent it -> it?.letterFrom }),
  OUTGOING('Исходящие', 'sign out', { LetterContent it -> it?.letterTo }),
  DRAFT('Черновики', 'file text outline', { LetterContent it -> it?.letterTo }),
  TRASH('Корзина', 'trash', { LetterContent it -> it?.letterFrom ? it.letterFrom : it?.letterTo });

  String title
  String icon
  Closure getAddress

  LetterType(String title, String icon, Closure getAddress) {
    this.getAddress = getAddress
    this.icon = icon
    this.title = title
  }
}