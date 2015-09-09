package ru.srms.larp.platform.game.mail

// TODO встроить правила вывода писем
enum LetterType {
  INBOX('Входящие', 'От кого', 'sign in', { LetterContent it -> it?.letterFrom }),
  OUTGOING('Исходящие', 'Кому', 'sign out', { LetterContent it -> it?.letterTo }),
  DRAFT('Черновики', 'Кому', 'file text outline', { LetterContent it -> it?.letterTo }),
  TRASH('Корзина', 'От кого / Кому', 'trash', { LetterContent it -> it?.letterFrom ? it.letterFrom : it?.letterTo });

  String title
  String targetName
  String icon
  Closure getAddress

  LetterType(String title, String targetName, String icon, Closure getAddress) {
    this.getAddress = getAddress
    this.icon = icon
    this.title = title
    this.targetName = targetName
  }
}