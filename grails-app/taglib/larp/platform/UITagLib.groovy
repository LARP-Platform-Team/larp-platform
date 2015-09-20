package larp.platform

class UITagLib {
  static namespace = "ui"

  /**
   * @attr remote set to true if form is remote
   * @attr css class for icon
   * @attr css classes
   * @attr name name of the button
   */
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

    def name = ''
    if(attrs.name) {
      name = " name=\"${attrs.name}\""
    }

    def loader = attrs.remote == "true" ? "<div class=\"ui mini inline loader\"></div>" : ""


    def title = attrs.title ? ' title="' + attrs.title + '"' : '';

    out << '<button class="' + classes + '" type="submit"' + title + name + '>' + icon + loader + body() + '</button>'
  }

  def message = {attrs, body ->
    out << render(template: '/shared/semantic/message',
                   model: [type: attrs.type, icon: attrs.icon, title: attrs.title?:null, messageText: body()])
  }
}
