package larp.platform

class UITagLib {
  static namespace = "ui"

  def submit = { attrs, body ->
    def classes = 'ui submit button'
    def icon = ''
    def text = body()

    if(attrs.class)
      classes += " ${attrs.class}"

    if (text && text.toString().length() > 0)
      classes += ' labeled'

    if(attrs.icon) {
      classes += ' icon'
      icon = '<i class="' + attrs.icon + ' icon"></i>'
    }

    def title = attrs.title ? 'title="' + attrs.title + '"' : '';

    out << '<button class="' + classes + '" type="submit"' + title + '>' + icon + body() + '</button>'
  }

  def message = {attrs, body ->
    out << render(template: '/shared/semantic/message',
                   model: [type: attrs.type, icon: attrs.icon, title: attrs.title?:null, messageText: body()])
  }
}
