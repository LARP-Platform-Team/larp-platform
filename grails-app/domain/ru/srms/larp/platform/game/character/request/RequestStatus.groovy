package ru.srms.larp.platform.game.character.request

/**
 * Created by Treble Snake on 21.09.2015.
 */
enum RequestStatus {

  DRAFT,
  SENT,
  REVIEW,
  REOPEN,
  ACCEPTED,
  DONE,
  DECLINED;

  private IRequestState data

  static {
    values().each { it.data = RequestStateFactory.get(it) }
  }

  IRequestState getData() {
    return data
  }
}