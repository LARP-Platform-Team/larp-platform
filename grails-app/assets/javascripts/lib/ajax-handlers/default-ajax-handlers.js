//= require custom-ajax-handlers

function initAjax() {
    basicAjaxSetup();
    initGlobalAjaxHandlers();
    initDefaultAjaxHandlers();
    registerCustomAjaxHandlers()
}

function alertAjaxError(text) {
    alert(text + ' ����������, ��������� � ���� (��������������) � ����������, ��� ��� ����� :)');
}

function basicAjaxSetup() {
    $.ajaxSetup({
        dataType: 'html',
        method: 'POST'
    });
}

function initGlobalAjaxHandlers() {
    $(document)
        .ajaxError(function (event, jqxhr, settings, thrownError) {
            if (jqxhr.status != 400) {
                alertAjaxError('��������� ������ �� ����� ���������� �������.');
                console.error(event, jqxhr, settings, thrownError)
            }
        })
        .ajaxSend(function (event, jqxhr, settings) {
            if (event.target.activeElement) {
                var target = $(event.target.activeElement);
                target.find('.ui.loader').addClass('active');
                if (target.is('.icon') && target.is(':not(.labeled)'))
                    target.find('.icon').hide()
            }
        })
        .ajaxComplete(function (event, jqxhr, settings) {
            if (event.target.activeElement) {
                var target = $(event.target.activeElement);
                target.find('.ui.loader').removeClass('active');
                if (target.is('.icon') && target.is(':not(.labeled)'))
                    target.find('.icon').show()
            }
        });
}

// default remote links and forms handlers
function initDefaultAjaxHandlers() {

    /**
     * ���������� ����� �������� ���������� ����� Ajax ������ � DOM
     * @param activeItem �������, ��������� ajax-������
     * @param data ���������� � ����� �� ������ ������
     * @param targetParent �������, ���� ����� ���������� ������
     */
    function beforeInsertData(activeItem, data, targetParent) {
        if (activeItem.hasClass('larp-ajax-self-delete')) {
            if (activeItem.popup('exists'))
                activeItem.popup('destroy')
        }
    }

    /**
     * ���������� ����� ������� ���������� ����� Ajax ������ � DOM
     * @param targetParent �������, ���� ����� ���������� ������
     */
    function afterInsertData(targetParent) {
        targetParent.find('.ui[title]').popup();
    }

    /**
     * ��������� � ������� ��������� ����������� ����������
     * @param request ajax-������ (promise)
     * @param element �������, �������������� ������
     */
    function withResultHandlers(request, element) {
        var successBlock = $("#" + element.attr('data-output-success'));
        var errorBlock = $("#" + element.attr('data-output-failure'));
        request
            .done(function (data) {
                if (successBlock.size() > 0) {
                    beforeInsertData(element, data);
                    successBlock.html(data);
                    afterInsertData(successBlock)
                }

                if (errorBlock.size() > 0)
                    errorBlock.empty();
            })
            .fail(function (data) {
                if (errorBlock.size() > 0) {
                    if (data.responseText != undefined)
                        errorBlock.html(data.responseText);
                    else {
                        alertAjaxError("��������� ����������� ������.");
                        console.error(data)
                    }
                }
            });
    }

    var defaultUrlClickHandler = function (event) {
        withResultHandlers($.ajax({
            url: $(this).attr('href')
        }), $(this));

        event.preventDefault()
    };

    $('a.larp-ajax').click(defaultUrlClickHandler);
    $('.larp-ajax-container')
        .find('a.larp-ajax').unbind("click")
        .end()
        .on("click", "a.larp-ajax", defaultUrlClickHandler);

    $('form.larp-ajax').submit(function (event) {
        withResultHandlers($.ajax({
            url: $(this).attr('action'),
            data: $(this).serialize()
        }), $(this));

        // TODO why not event.preventDefault() ?
        return false
    });
}