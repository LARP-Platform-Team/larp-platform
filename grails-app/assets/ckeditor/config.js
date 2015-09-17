/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';

    // Toolbar groups configuration.
    config.toolbarGroups = [
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ] },
        { name: 'tools' },
        { name: 'about' },
        '/',
        { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
        { name: 'paragraph', groups: [ 'list', 'blocks', 'align', 'bidi' ] }, //indent
        '/',
        { name: 'links' },
        { name: 'insert' },
        { name: 'styles' },
        // Not present
        '/',
        { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
        { name: 'forms' },
        { name: 'others' },
        { name: 'colors' }
    ];
};
