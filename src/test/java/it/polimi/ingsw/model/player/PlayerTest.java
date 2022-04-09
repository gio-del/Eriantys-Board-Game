package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.GameLimit;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.HallObserver;
import static it.polimi.ingsw.model.pawns.PawnColor.*;
import static it.polimi.ingsw.model.player.Assistant.*;
import static it.polimi.ingsw.model.player.TowerColor.*;
import static it.polimi.ingsw.model.player.Wizard.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the {@link Player} methods.
 */
public class PlayerTest {
    private Player player1;
    private Player player2;
    private Player player3;
    private Pawns pawns;
    private Island island;

    @BeforeEach
    void setUp() {
        GameLimit gameLimit = new GameLimit(false);
        player1 = new Player("Mario", WIZ1, BLACK,gameLimit);
        player2 = new Player("Lorenzo",WIZ2, WHITE,gameLimit);
        player3 = new Player("Lorenzo",WIZ2, WHITE,gameLimit);
        player1.getSchool().getEntrance().addColor(GREEN,4);
        player1.getSchool().getEntrance().addColor(BLUE,1);
        pawns = new Pawns();
        pawns.addColor(GREEN,4);
        pawns.addColor(BLUE,1);
        island = new Island();
    }

    @AfterEach
    void tearDown() {
        HallObserver.resetInstance();
    }

    /**
     * This method tests getPlayerName.
     */
    @Test
    void playerNameTest() {
        assertEquals("Mario",player1.getPlayerName());
        assertEquals("Lorenzo",player2.getPlayerName());
    }

    /**
     * This method tests getColor.
     */
    @Test
    void colorTest() {
        assertEquals(BLACK,player1.getColor());
        assertEquals(WHITE,player2.getColor());
    }

    /**
     * This method tests getWizard.
     */
    @Test
    void wizardTest() {
        assertEquals(WIZ1,player1.getWizard());
        assertEquals(WIZ2,player2.getWizard());
    }

    /**
     * This method tests getPlayerBank.
     */
    @Test
    void initialBankTest() {
        assertEquals(1,player1.getPlayerBank());
        assertEquals(1,player2.getPlayerBank());
    }

    /**
     * This method tests getHand and playAssistant.
     */
    @Test
    void playAssistantTest() {
        int valueTest;
        int initialHandSize = player1.getHand().size();
        valueTest = player1.playAssistant(LION);
        assertEquals(10,valueTest);
        assertEquals(LION, player1.getLastPlayedAssistant());
        assertEquals(initialHandSize,player1.getHand().size()+1);
    }

    /**
     * This method tests equals.
     */
    @Test
    void equalsTest() {
        boolean equals;
        equals = player2.equals(player1);
        assertFalse(equals);
        equals = player2.equals(player3);
        assertTrue(equals);
    }

    /**
     * This method tests moveToIsland.
     */
    @Test
    void moveToIsland() {
        assertEquals(pawns,player1.getSchool().getEntrance());
        player1.moveFromEntranceToIsland(pawns,island);
        assertEquals(pawns,island.getStudents());
        assertEquals(0, player1.getSchool().getEntrance().totalElements());
    }
}