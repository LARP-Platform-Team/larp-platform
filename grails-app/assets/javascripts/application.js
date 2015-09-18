// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery
//= require_tree .
//= require underscore-min
//= require jquery.json.min
//= require ../semantic/semantic
//= require ../ckeditor/ckeditor
//= require ../ckeditor/adapters/jquery
//= require ../chosen/chosen.jquery.min
//= require ../jquery-ui/jquery-ui.min
//= require_self

if (typeof jQuery !== 'undefined') {
	$(function () {
        initSemanticUI();
        initRichTextEditor();
        initAjax();
        emailAddressAutocomplete()
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

		$('.ui[title]').popup();

		$('.ui.accordion').accordion({exclusive: false})

		$('.ui.tabular.menu .item').tab();

        $('select.dropdown').chosen({
            no_results_text: "Ничего не найдено",
            disable_search_threshold: 5,
            inherit_select_classes: true,
            placeholder_text_multiple: "Выберите значения",
            placeholder_text_single: "Выберите значение",
            search_contains: true,
            allow_single_deselect: true
        });
    }

    function initRichTextEditor() {
		$('textarea.rich:not(.simple)').ckeditor({
			language: 'ru',
            removePlugins: 'justify,indentblock'
		});

        $('textarea.simple.rich').ckeditor({
            language: 'ru',
            removePlugins: 'justify,identblock,blockquote,image,indentblock,indentlist,' +
            'link,list,liststyle,pagebreak,pastefromword,smiley,stylescombo,' +
            'table,tableresize,tabletools,specialchar,format,horizontalrule'
        });
    }

    function emailAddressAutocomplete() {
        function split(val) {
            return val.split(/,\s*/);
        }

        function extractLast(term) {
            return split(term).pop();
        }

        function filter(options, selected) {
            return _.difference(options, split(selected))
        }

        $(".autocompleteMulti").each(function () {
            var input = $(this);
            var options = jQuery.secureEvalJSON(input.attr('data-options'));
            input
                // don't navigate away from the field on tab when selecting an item
                // don't navigate away from the field on tab when selecting an item
                .bind("keydown", function (event) {
                    if (event.keyCode === $.ui.keyCode.TAB &&
                        $(this).autocomplete("instance").menu.active) {
                        event.preventDefault();
                    }
                })
                .autocomplete({
                    minLength: 0,
                    source: function (request, response) {
                        // delegate back to autocomplete, but extract the last term
                        response($.ui.autocomplete.filter(
                            filter(options, input.val()), extractLast(request.term)));
                    },
                    focus: function () {
                        // prevent value inserted on focus
                        return false;
                    },
                    select: function (event, ui) {
                        var terms = split(this.value);
                        // remove the current input
                        terms.pop();
                        // add the selected item
                        terms.push(ui.item.value);
                        // add placeholder to get the comma-and-space at the end
                        terms.push("");
                        this.value = terms.join(", ");
                        return false;
                    }
                });
        })

    }

}
