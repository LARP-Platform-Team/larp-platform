package larp.platform

class UITagLib {
  static namespace = "ui"

  def submit = { attrs, body ->
    def classes = 'ui submit button'
    def icon = ''
    if(attrs.icon) {
      classes += ' labeled icon'
      icon = '<i class="ui icon ' + attrs.icon + '"></i>'
    }

    out << '<button class="' + classes +'" type="submit">' + icon + body() + '</button>'
  }

  def message = {attrs, body ->
    out << render(template: '/shared/semantic/message',
                   model: [type: attrs.type, icon: attrs.icon, title: attrs.title?:null, messageText: body()])
  }
}
