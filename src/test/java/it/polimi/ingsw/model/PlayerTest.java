package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import static it.polimi.ingsw.model.player.Assistant.*;
import static it.polimi.ingsw.model.player.TowerColor.*;
import static it.polimi.ingsw.model.player.Wizard.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        player1 = new Player("Mario", WIZ1, BLACK);
        player2 = new Player("Lorenzo",WIZ2, WHITE);
        player3 = new Player("Lorenzo",WIZ2, WHITE);
    }

    @Test
    void playerNameTest() {
        assertEquals("Mario",player1.getPlayerName());
        assertEquals("Lorenzo",player2.getPlayerName());
    }

    @Test
    void colorTest() {
        assertEquals(BLACK,player1.getColor());
        assertEquals(WHITE,player2.getColor());
    }

    @Test
    void wizardTest() {
        assertEquals(WIZ1,player1.getWizard());
        assertEquals(WIZ2,player2.getWizard());
    }

    @Test
    void initialBankTest() {
        assertEquals(1,player1.getPlayerBank());
        assertEquals(1,player2.getPlayerBank());
    }

    @Test
    void playAssistantTest() {
        int valueTest;
        int initialHandSize = player1.getHand().size();
        valueTest = player1.playAssistant(LION);
        assertEquals(10,valueTest);
        assertEquals(player1.getLastPlayedAssistant(),LION);
        assertEquals(initialHandSize,player1.getHand().size()+1);
    }

    @Test
    void equalsTest() {
        boolean equals;
        equals = player2.equals(player1);
        assertFalse(equals);
        equals = player2.equals(player3);
        assertTrue(equals);
    }
}
