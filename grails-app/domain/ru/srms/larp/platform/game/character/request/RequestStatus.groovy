package ru.srms.larp.platform.game.character.request

/**
 * Created by Treble Snake on 21.09.2015.
 */
enum RequestStatus {

  DRAFT('Черновик', true),
  SENT('Отправлена на рассмотрение', true),
  REVIEW('На рассмотрении'),
  REOPEN('Требуются уточнения', true),
  ACCEPTED('Принята, создается персонаж'),
  DONE('Персонаж создан'),
  DECLINED('Отклонена');

  private String title
  private Boolean editable

  RequestStatus(String title, Boolean editable = false) {
    this.title = title
    this.editable = editable
  }
}