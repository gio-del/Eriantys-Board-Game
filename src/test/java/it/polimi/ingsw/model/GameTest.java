package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = Game.getInstance();

        game.depositInBank(10);
        Player p1 = new Player("Luca", Wizard.WIZ1, TowerColor.BLACK);
        Player p2 = new Player("Marco", Wizard.WIZ2,TowerColor.GRAY);

        game.addPlayer(p1);
        game.addPlayer(p2);
    }

    @AfterEach
    void tearDown() {
        Game.resetInstance();
    }

    @Test
    void getPlayerByName_ifPresent() {
        assertEquals("Luca",game.getPlayerByName("Luca").getPlayerName());
    }

    @Test
    void getPlayerByName_ifNotPresent() {
        assertNull(game.getPlayerByName("Tony"));
    }

    @Test
    void getPlayerByTowerColor_ifPresent() {
        assertEquals("Luca",game.getPlayerByTowerColor(TowerColor.BLACK).getPlayerName());
    }

    @Test
    void getPlayerByTowerColor_ifNotPresent() {
        assertNull(game.getPlayerByTowerColor(TowerColor.WHITE));
    }

    @Test
    void depositTest() {
        assertEquals(10,game.getGeneralBank());
    }
}
