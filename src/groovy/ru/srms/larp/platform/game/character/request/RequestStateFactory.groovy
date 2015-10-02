package ru.srms.larp.platform.game.character.request

import static ru.srms.larp.platform.game.character.request.RequestStatus.*

/**
 * Created by Treble Snake on 29.09.2015.
 */
class RequestStateFactory {
  public static def PRESETS = [
      (DRAFT)   : new RequestStateImpl('Черновик', 'grey')
          .setEditable(true)
          .addNext(SENT),

      (SENT)    : new RequestStateImpl('Отправлена на рассмотрение', 'blue')
          .addNext(REVIEW),

      (REVIEW)  : new RequestStateImpl('На рассмотрении', 'yellow')
          .addNext(DONE, ACCEPTED, REOPEN, DECLINED)
          .setSelectCharacter(true),

      (REOPEN)  : new RequestStateImpl('Требуются уточнения', 'orange')
          .setEditable(true)
          .addNext(DRAFT, SENT),

      (ACCEPTED): new RequestStateImpl('Принята, создается персонаж', 'olive')
          .addNext(DONE, REOPEN)
          .setSelectCharacter(true),

      (DONE)    : new RequestStateImpl('Персонаж создан', 'green'),

      (DECLINED): new RequestStateImpl('Отклонена', 'red')
  ]

  public static IRequestState get(RequestStatus status) {
    if (!PRESETS.containsKey(status))
      throw new RuntimeException("Unknown state")

    return PRESETS.get(status)
  }
}
