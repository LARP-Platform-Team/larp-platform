function registerCustomAjaxHandlers() {
    initAddressBookAjax()
}

function initAddressBookAjax() {
    $('a.addAddressBookEntry')
        .unbind('click')
        .click(function (event) {
            var link = $(this);
            $.ajax({
                dataType: 'json',
                url: link.attr('href')
            })
                .done(function (data) {
                    if (data.success) {
                        link.parents('.lettersList')
                            .find('a.addAddressBookEntry[rel=\'' + link.attr('rel') + '\']')
                            .popup('destroy')
                            .remove();
                        link.popup('destroy').remove()
                    } else
                        alertAjaxError("������. �� ������� �������� ������.")
                })
                .fail(function (data) {
                    alertAjaxError("������. �� ������� ��������� ������.")
                });

            event.preventDefault()
        })
}