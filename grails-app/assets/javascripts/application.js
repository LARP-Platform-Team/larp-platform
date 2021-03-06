// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require vendor/jquery-2.1.4
//= require ../semantic/semantic
//= require ../ckeditor/ckeditor
//= require ../ckeditor/adapters/jquery
//= require ../chosen/chosen.jquery.min
//= require ../jquery-ui/jquery-ui.min
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {

    var CHOSEN_OPTIONS = {
        no_results_text: "Ничего не найдено",
        disable_search_threshold: 5,
        inherit_select_classes: true,
        placeholder_text_multiple: "Выберите значения",
        placeholder_text_single: "Выберите значение",
        search_contains: true,
        allow_single_deselect: true
    };

    $(function () {
        initSemanticUI();
        initRichTextEditor();
        initAjax();
        initEmailAddressAutocomplete();
        initMiscGUI()
    });

    function initMiscGUI() {
        // request form config page
        var SELECT_FIELD_TYPE = "SELECT";
        $('#requestFieldType').change(function(event){
            if($(this).val() == SELECT_FIELD_TYPE)
                $('#requestFieldDataWrapper').fadeIn();
            else
                $('#requestFieldDataWrapper').fadeOut()
        });

        // request editing page

        //manage visibility of role fields' blocks
        $("#character_request_roles").on('change', function (event, params) {
            if (params.selected) {
                $("#role_" + params.selected + "_fields").fadeIn(400, function () {
                    $("#role_" + params.selected + "_fields")
                        .find('select.dropdown').chosen(CHOSEN_OPTIONS)
                        // fix for a "is not focusable" bug for hidden required fields
                        .end().find('[data-is-required=true]').attr('required', 'required');
                });

            }
            else if (params.deselected) {
                $("#role_" + params.deselected + "_fields").fadeOut()
                    // fix for a "is not focusable" bug for hidden required fields
                    .find('[required]').removeAttr('required');
            }
        });
    }

    function initSemanticUI() {
		$('.ui.message .close').on('click', function () {
			$(this)
				.closest('.message')
				.transition('fade');
		});

		$('.ui.checkbox, .ui.radio.checkbox').checkbox();

		$('.ui[title]').popup();
		$('.ui.html-popup').popup({inline: true});

        $('.ui.accordion').accordion({exclusive: false});

		$('.ui.tabular.menu .item').tab();

        $('select.dropdown:visible').chosen(CHOSEN_OPTIONS);
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

    function initEmailAddressAutocomplete() {
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
