// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery
//= require_tree .
//= require ../semantic/semantic
//= require ../ckeditor/ckeditor
//= require ../ckeditor/adapters/jquery
//= require_self

if (typeof jQuery !== 'undefined') {
	$(function () {
        initSemanticUI();
        initRichTextEditor();
        initAjax()
    });

    function initAjax() {
        function showError(text) {
            alert(text + ' Пожалуйста, свяжитесь с нами (разработчиками) и расскажите, как так вышло :)');
        }

        // TODO Set it up after custom ajax implemented
        //$.ajaxSetup({
        //    dataType: 'json',
        //    method: 'POST'
        //});

        $(document)
            .ajaxError(function (event, jqxhr, settings, thrownError) {
                // TODO delete after custom ajax implemented
                if(jqxhr.status != 400) {
                    showError('Произошла ошибка во время выполнения запроса.')
                    console.error(event, jqxhr, settings, thrownError)
                }
            })
            .ajaxSend(function (event, jqxhr, settings) {
                if(event.target.activeElement) {
                    $(event.target.activeElement)
                        .find('.ui.loader').addClass('active')
                }
            })
            .ajaxComplete(function (event, jqxhr, settings) {
                if(event.target.activeElement) {
                    $(event.target.activeElement)
                        .find('.ui.loader').removeClass('active')
                }
            });

        $('a.addAddressBookEntry').click(function (event) {
            var link = $(this);
            $.ajax({
                dataType: 'json',
                method: 'POST',
                url: link.attr('href')
            })
                .done(function (data) {
                    if (data.success) {
                        link.parents('.lettersList')
                            .find('a.addAddressBookEntry[rel=\''+link.attr('rel')+'\']')
                            .popup('destroy')
                            .remove();
                        link.popup('destroy').remove()
                    } else
                        showError("Ошибка. Не удалось добавить запись.")
                });

            event.preventDefault()
        })
    }

    function initSemanticUI() {
		$('.ui.message .close').on('click', function () {
			$(this)
				.closest('.message')
				.transition('fade');
		});

		$('.ui.checkbox, .ui.radio.checkbox').checkbox();

		$('select.dropdown').dropdown();
		$('.ui[title]').popup();

		$('.ui.accordion').accordion({exclusive: false})

		$('.ui.tabular.menu .item').tab();
    }

    function initRichTextEditor() {
		$('textarea.rich').ckeditor({
			language: 'ru'
		});
    }
}
