package ru.srms.larp.platform.game.mail

// TODO встроить правила вывода писем
enum LetterType {
  INBOX('Входящие'),
  OUTGOING('Исходящие'),
  DRAFT('Черновики'),
  TRASH('Корзина');

  String title

  LetterType(String title) {
    this.title = title
  }

  @Override
  String toString() {
    title
  }
}