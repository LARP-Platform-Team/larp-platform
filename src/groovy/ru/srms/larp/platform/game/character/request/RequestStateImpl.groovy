package ru.srms.larp.platform.game.character.request

/**
 * Created by Treble Snake on 29.09.2015.
 */
class RequestStateImpl implements IRequestState {

  String name
  String labelColor
  boolean editable = false
  boolean selectCharacter = false
  List<RequestStatus> next = []

  RequestStateImpl() {
  }

  RequestStateImpl(String name, String labelColor) {
    this.name = name
    this.labelColor = labelColor
  }

  @Override
  String getName() {
    return name
  }

  @Override
  IRequestState setName(String name) {
    this.name = name
    return this
  }

  @Override
  String getLabelColor() {
    return labelColor
  }

  @Override
  IRequestState setLabelColor(String labelColor) {
    this.labelColor = labelColor
    return this
  }

  @Override
  boolean isEditable() {
    return editable
  }

  @Override
  boolean isSelectCharacter() {
    return selectCharacter
  }

  @Override
  IRequestState setEditable(boolean editable) {
    this.editable = editable
    return this
  }

  @Override
  IRequestState setSelectCharacter(boolean selectCharacter) {
    this.selectCharacter = selectCharacter
    return this
  }

  @Override
  IRequestState addNext(RequestStatus... statuses) {
    if(statuses)
      next.addAll(statuses)
    return this
  }

  @Override
  List<RequestStatus> getNextAllowed() {
    return next
  }

  @Override
  boolean canChangeTo(RequestStatus status) {
    return next.contains(status)
  }

}
