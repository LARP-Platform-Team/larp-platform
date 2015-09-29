package ru.srms.larp.platform.game.character.request

import org.apache.commons.lang.StringUtils

/**
 * Created by Treble Snake on 21.09.2015.
 */
enum FieldType {
  SIMPLE_TEXT('Однострочный текст', { FormFieldValue value ->
    !(value.field.required && StringUtils.isBlank(value.value))
  }),

  TEXTAREA('Многострочный текст', { FormFieldValue value ->
    !(value.field.required && StringUtils.isBlank(value.value))
  }, true, { Map data ->
    data?.value && data?.cleaner ? data.cleaner.cleanHtml(data.value, 'simple-rich-text') : null
  }),

  CHECKBOX('Да/Нет', { FormFieldValue value ->
    !value.field.required || Boolean.valueOf(value.value)
  }, true, { Map data -> data?.value ? Boolean.TRUE.toString() : Boolean.FALSE.toString() }),

  SELECT('Поле выбора', { FormFieldValue value ->
    if (value.field.required && StringUtils.isBlank(value.value))
      return false;

    def options = value.field.data.tokenize(';')
    return options.any { it.equals(value.value) }
  }),

  // TODO implement label
//  LABEL('Надпись', { true }, false);

  private String title;
  private Boolean input;
  public Closure<Boolean> validate
  public Closure transform

  FieldType(String title, Closure<Boolean> validate = { FormFieldValue value -> true },
            Boolean input = true, Closure transform = { Map data -> data?.value }) {
    this.title = title
    this.input = input
    this.validate = validate
    this.transform = transform
  }

  String getTitle() {
    return title
  }

  Boolean isInput() {
    return input
  }
}