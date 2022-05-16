package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Place;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwapActionTest {
    Place from;
    Place to;
    Pawns fromEntrance;
    Pawns toHall;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.addPlayer("Luigi", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Marco", Wizard.FLAME_MAGICIAN, TowerColor.WHITE);
        game.init();

        fromEntrance = game.getPlayerByName("Luigi").getSchool().getEntrance();
        from = game.getPlayerByName("Luigi").getSchool().getEntranceAsPlace();

        toHall = game.getPlayerByName("Luigi").getSchool().getHall();
        to = game.getPlayerByName("Luigi").getSchool().getHallAsPlace();

        Pawns pawns = new Pawns(1, 1, 1, 1, 1);
        from.add(pawns); //FROM: 1 1 1 1 1
        to.add(pawns);
        to.add(pawns); //TO: 2 2 2 2 2
    }

    /**
     * Test the successful swap
     */
    @Test
    void swapActionIfOk_1() {
        List<PawnColor> swapList = List.of(PawnColor.RED, PawnColor.BLUE, PawnColor.GREEN, PawnColor.YELLOW);
        //EXPECTED AFTER SWAP
        // FROM: 0 0 2 1 2
        // TO: 3 3 1 2 1
        assertTrue(new SwapAction(from, to, swapList).apply());
        assertEquals(new Pawns(0, 0, 2, 1, 2), fromEntrance);
        assertEquals(new Pawns(3, 3, 1, 2, 1), toHall);
    }

    /**
     * Test the successful swap
     */
    @Test
    void swapActionIfOk_2() {
        List<PawnColor> swapList = List.of(PawnColor.RED, PawnColor.BLUE);
        //EXPECTED AFTER SWAP
        // FROM: 1 0 1 1 2
        // TO: 2 3 2 2 1
        assertTrue(new SwapAction(from, to, swapList).apply());
        assertEquals(new Pawns(1, 0, 1, 1, 2), fromEntrance);
        assertEquals(new Pawns(2, 3, 2, 2, 1), toHall);
    }

    /**
     * Swap fails because pawns has different sizes
     */
    @Test
    void moveActionKO_1() {
        List<PawnColor> swapList = List.of(PawnColor.RED, PawnColor.BLUE, PawnColor.YELLOW);
        assertFalse(new SwapAction(from, to, swapList).apply());
        //check that nothing is changed
        assertEquals(new Pawns(1, 1, 1, 1, 1), fromEntrance);
        assertEquals(new Pawns(2, 2, 2, 2, 2), toHall);
    }

    /**
     * Swap fails because pawns has same size but pawns from "From" is not contained in "From"
     */
    @Test
    void moveActionKO_2() {
        List<PawnColor> swapList = List.of(PawnColor.RED, PawnColor.BLUE, PawnColor.RED, PawnColor.GREEN);
        assertFalse(new SwapAction(from, to, swapList).apply());
        //check that nothing is changed
        assertEquals(new Pawns(1, 1, 1, 1, 1), fromEntrance);
        assertEquals(new Pawns(2, 2, 2, 2, 2), toHall);
    }

    /**
     * Swap fails because pawns has same size but pawns from "To" is not contained in "To"
     */
    @Test
    void moveActionKO_3() {
        List<PawnColor> swapList = List.of(PawnColor.GREEN, PawnColor.RED, PawnColor.BLUE, PawnColor.RED, PawnColor.YELLOW, PawnColor.RED);
        assertFalse(new SwapAction(from, to, swapList).apply());
        //check that nothing is changed
        assertEquals(new Pawns(1, 1, 1, 1, 1), fromEntrance);
        assertEquals(new Pawns(2, 2, 2, 2, 2), toHall);
    }
}
