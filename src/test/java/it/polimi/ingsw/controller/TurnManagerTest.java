package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.server.TurnManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TurnManagerTest {
    private TurnManager turnManager;
    private Game game;

    @BeforeEach
    void setUp() {
        turnManager = new TurnManager();

        game = new Game(3,false);
        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Marco",Wizard.SORCERER, TowerColor.WHITE);
        game.addPlayer("Lorenzo",Wizard.WITCH,TowerColor.GRAY);



    }

    /**
     * Tests action order logic
     */
    @Test
    void actionOrderTest_1() {
        game.setCurrentPlayer(game.getPlayerByName("Marco"));
        game.playAssistant(Assistant.ELEPHANT); //value:2

        game.setCurrentPlayer(game.getPlayerByName("Luca"));
        game.playAssistant(Assistant.LION); //value:10

        game.setCurrentPlayer(game.getPlayerByName("Lorenzo"));
        game.playAssistant(Assistant.TURTLE); //value:1
        turnManager.setActionOrder(game.getNicknameMapAssistant());
        List<String> order = turnManager.getPlayerOrder();

        assertEquals("Lorenzo",order.get(0));
        assertEquals("Marco",order.get(1));
        assertEquals("Luca",order.get(2));
    }

    /**
     * Tests the case of two players playing the same Assistant Card: order of playing counts!
     */
    @Test
    void actionOrderTest_2() {
        game.setCurrentPlayer(game.getPlayerByName("Marco"));
        game.playAssistant(Assistant.TURTLE); //value:1

        game.setCurrentPlayer(game.getPlayerByName("Luca"));
        game.playAssistant(Assistant.LION); //value:10

        game.setCurrentPlayer(game.getPlayerByName("Lorenzo"));
        game.playAssistant(Assistant.TURTLE); //value:1
        turnManager.setActionOrder(game.getNicknameMapAssistant());
        List<String> order = turnManager.getPlayerOrder();

        assertEquals("Marco",order.get(0));
        assertEquals("Lorenzo",order.get(1));
        assertEquals("Luca",order.get(2));
    }

    @Test
    void actionOrderTest_3() {
        game.setCurrentPlayer(game.getPlayerByName("Marco"));
        game.playAssistant(Assistant.DOG); //value:3

        game.setCurrentPlayer(game.getPlayerByName("Luca"));
        game.playAssistant(Assistant.CROCODILE); //value:5

        game.setCurrentPlayer(game.getPlayerByName("Lorenzo"));
        game.playAssistant(Assistant.OSTRICH); //value:9
        turnManager.setActionOrder(game.getNicknameMapAssistant());
        List<String> order = turnManager.getPlayerOrder();

        assertEquals("Marco",order.get(0));
        assertEquals("Luca",order.get(1));
        assertEquals("Lorenzo",order.get(2));
    }
}
