<%@ page import="ru.srms.larp.platform.game.mail.LetterRef; ru.srms.larp.platform.game.mail.LetterType" %>
<g:set var="letter" value="${letter as LetterRef}"/>

<g:if test="${letter.content?.sender && LetterType.INBOX.equals(letter.type) \
 && letter.content.sender.id != letter.mailbox.id && !letter.mailbox.hasInBook(letter.content.sender)}">
  <ingame:link class="ui icon compact addAddressBookEntry button" rel="${letter.content.sender.id}"
               action="addAddressBookEntry" resource="${letter.mailbox}" params="['target.id': letter.content.sender.id]"
               title="Добавить в адресную книгу">
    <i class="plus icon"></i><i class="book icon"></i><div class="ui mini inline loader"></div>
  </ingame:link>
</g:if>