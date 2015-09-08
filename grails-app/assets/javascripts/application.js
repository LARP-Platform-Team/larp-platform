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
		$('#spinner').ajaxStart(function () {
			$(this).fadeIn();
		}).ajaxStop(function () {
			$(this).fadeOut();
		});

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

		//$('textarea.rich').ckeditor({
		//	language: 'ru'
		//});
	});
}
