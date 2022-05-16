package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
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
        turnManager = new TurnManager(game, new GameController());

        game = new Game();
        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Marco", Wizard.SORCERER, TowerColor.WHITE);
        game.addPlayer("Lorenzo", Wizard.WITCH, TowerColor.GRAY);

        game.init();
    }

    /**
     * Tests action order logic
     */
    @Test
    void actionOrderTest_1() {
        game.setCurrentPlayer("Marco");
        game.playAssistant(Assistant.ELEPHANT); //value:2

        game.setCurrentPlayer("Luca");
        game.playAssistant(Assistant.LION); //value:10

        game.setCurrentPlayer("Lorenzo");
        game.playAssistant(Assistant.TURTLE); //value:1
        turnManager.setActionOrder(game.getPlayedAssistantMap());
        List<String> order = turnManager.getPlayersOrder();

        assertEquals("Lorenzo", order.get(0));
        assertEquals("Marco", order.get(1));
        assertEquals("Luca", order.get(2));
    }

    /**
     * Tests the case of two players playing the same Assistant Card: order of playing counts!
     */
    @Test
    void actionOrderTest_2() {
        game.setCurrentPlayer("Marco");
        game.playAssistant(Assistant.TURTLE); //value:1

        game.setCurrentPlayer("Luca");
        game.playAssistant(Assistant.LION); //value:10

        game.setCurrentPlayer("Lorenzo");
        game.playAssistant(Assistant.TURTLE); //value:1
        turnManager.setActionOrder(game.getPlayedAssistantMap());
        List<String> order = turnManager.getPlayersOrder();

        assertEquals("Marco", order.get(0));
        assertEquals("Lorenzo", order.get(1));
        assertEquals("Luca", order.get(2));
    }

    @Test
    void actionOrderTest_3() {
        game.setCurrentPlayer("Marco");
        game.playAssistant(Assistant.DOG); //value:3

        game.setCurrentPlayer("Luca");
        game.playAssistant(Assistant.CROCODILE); //value:5

        game.setCurrentPlayer("Lorenzo");
        game.playAssistant(Assistant.OSTRICH); //value:9
        turnManager.setActionOrder(game.getPlayedAssistantMap());
        List<String> order = turnManager.getPlayersOrder();

        assertEquals("Marco", order.get(0));
        assertEquals("Luca", order.get(1));
        assertEquals("Lorenzo", order.get(2));
    }

    @Test
    void planningOrderTest_1() {
        //NOTE: TABLE ORDER IS "LUCA","MARCO","LORENZO"
        game.setCurrentPlayer("Marco");
        game.playAssistant(Assistant.ELEPHANT); //value:2

        game.setCurrentPlayer("Luca");
        game.playAssistant(Assistant.LION); //value:10

        game.setCurrentPlayer("Lorenzo");
        game.playAssistant(Assistant.TURTLE); //value:1
        turnManager.setPlanningOrder(game.getPlayedAssistantMap(), game.getPlayers().stream().map(Player::getPlayerName).toList());
        List<String> order = turnManager.getPlayersOrder();

        assertEquals("Lorenzo", order.get(0));
        assertEquals("Luca", order.get(1));
        assertEquals("Marco", order.get(2));
    }

    /**
     * Tests the case of two players playing the same Assistant Card: order of playing counts!
     */
    @Test
    void planningOrderTest_2() {
        //NOTE: TABLE ORDER IS "LUCA","MARCO","LORENZO"
        game.setCurrentPlayer("Marco");
        game.playAssistant(Assistant.TURTLE); //value:1

        game.setCurrentPlayer("Luca");
        game.playAssistant(Assistant.TURTLE); //value:1

        game.setCurrentPlayer("Lorenzo");
        game.playAssistant(Assistant.LION); //value:10
        turnManager.setPlanningOrder(game.getPlayedAssistantMap(), game.getPlayers().stream().map(Player::getPlayerName).toList());
        List<String> order = turnManager.getPlayersOrder();

        assertEquals("Marco", order.get(0));
        assertEquals("Lorenzo", order.get(1));
        assertEquals("Luca", order.get(2));
    }

    @Test
    void planningOrderTest_3() {
        //NOTE: TABLE ORDER IS "LUCA","MARCO","LORENZO"
        game.setCurrentPlayer("Marco");
        game.playAssistant(Assistant.ELEPHANT); //value:2

        game.setCurrentPlayer("Luca");
        game.playAssistant(Assistant.TURTLE); //value:1

        game.setCurrentPlayer("Lorenzo");
        game.playAssistant(Assistant.LION); //value:10
        turnManager.setPlanningOrder(game.getPlayedAssistantMap(), game.getPlayers().stream().map(Player::getPlayerName).toList());
        List<String> order = turnManager.getPlayersOrder();

        assertEquals("Luca", order.get(0));
        assertEquals("Marco", order.get(1));
        assertEquals("Lorenzo", order.get(2));
    }

    @Test
    void firstOrderTest() {
        turnManager.setFirstOrder(game.getPlayers().stream().map(Player::getPlayerName).toList());
        List<String> players = turnManager.getPlayersOrder();
        assertEquals(3, players.size());
    }
}
