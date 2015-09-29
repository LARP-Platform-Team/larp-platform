package ru.srms.larp.platform.game.character.request

/**
 * Created by Treble Snake on 29.09.2015.
 */
interface IRequestState {
  IRequestState setName(String name)
  IRequestState setLabelColor(String color)
  IRequestState setEditable(boolean editable)
  IRequestState setSelectCharacter(boolean selectCharacter)

  String getName()
  String getLabelColor()
  boolean isEditable()
  boolean isSelectCharacter()

  IRequestState addNext(RequestStatus ... statuses)

  List<RequestStatus> getNextAllowed()
  boolean canChangeTo(RequestStatus status)
}