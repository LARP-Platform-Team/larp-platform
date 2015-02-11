package ru.srms.larp.platform.game

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(GameController)
@Mock(Game)
class GameControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.gameInstanceList
        model.gameInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.gameInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def game = new Game()
        game.validate()
        controller.save(game)

        then: "The create view is rendered again with the correct model"
        model.gameInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        game = new Game(params)

        controller.save(game)

        then: "A redirect is issued to the play action"
        response.redirectedUrl == '/game/play/1'
        controller.flash.message != null
        Game.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "The play action is executed with a null domain"
        controller.play(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the play action"
        populateValidParams(params)
        def game = new Game(params)
        controller.play(game)

        then: "A model is populated containing the domain instance"
        model.gameInstance == game
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def game = new Game(params)
        controller.edit(game)

        then: "A model is populated containing the domain instance"
        model.gameInstance == game
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'PUT'
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/game/index'
        flash.message != null


        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def game = new Game()
        game.validate()
        controller.update(game)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.gameInstance == game

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        game = new Game(params).save(flush: true)
        controller.update(game)

        then: "A redirect is issues to the play action"
        response.redirectedUrl == "/game/play/$game.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/game/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def game = new Game(params).save(flush: true)

        then: "It exists"
        Game.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(game)

        then: "The instance is deleted"
        Game.count() == 0
        response.redirectedUrl == '/game/index'
        flash.message != null
    }
}
