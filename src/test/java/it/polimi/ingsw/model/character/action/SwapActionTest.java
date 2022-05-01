package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Place;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SwapActionTest {
    Place from;
    Place to;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.addPlayer("Luigi", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Marco", Wizard.FLAME_MAGICIAN, TowerColor.WHITE);
        game.init();

        from = game.getPlayerByName("Luigi").getSchool().getEntranceAsPlace();
        to = game.getPlayerByName("Luigi").getSchool().getHallAsPlace();

        Pawns pawns = new Pawns(1,1,1,1,1);
        from.add(pawns);
        to.add(pawns);
        to.add(pawns);
    }

    /**
     * Test the successful swap
     */
    @Test
    void swapActionIfOk() {

        Pawns pawnsToBeSwappedFrom = new Pawns(0,1,1,0,0);
        Pawns pawnsToBeSwappedTo = new Pawns(1,0,0,0,1);
        assertTrue(new SwapAction(from,to,pawnsToBeSwappedFrom,pawnsToBeSwappedTo).apply());
    }

    /**
     * Swap fails because pawns has different sizes
     */
    @Test
    void moveActionKO_1() {
        Pawns pawnsToBeSwappedFrom = new Pawns(1,1,1,0,0);
        Pawns pawnsToBeSwappedTo = new Pawns(1,0,0,0,1);
        assertFalse(new SwapAction(from,to,pawnsToBeSwappedFrom,pawnsToBeSwappedTo).apply());
    }

    /**
     * Swap fails because pawns has same size but pawns from "From" is not contained in "From"
     */
    @Test
    void moveActionKO_2() {
        Pawns pawnsToBeSwappedFrom = new Pawns(1,1,2,0,0);
        Pawns pawnsToBeSwappedTo = new Pawns(1,0,2,0,1);
        assertFalse(new SwapAction(from,to,pawnsToBeSwappedFrom,pawnsToBeSwappedTo).apply());
    }

    /**
     * Swap fails because pawns has same size but pawns from "To" is not contained in "To"
     */
    @Test
    void moveActionKO_3() {
        Pawns pawnsToBeSwappedFrom = new Pawns(1,1,1,1,0);
        Pawns pawnsToBeSwappedTo = new Pawns(1,0,3,0,0);
        assertFalse(new SwapAction(from,to,pawnsToBeSwappedFrom,pawnsToBeSwappedTo).apply());
    }
}
