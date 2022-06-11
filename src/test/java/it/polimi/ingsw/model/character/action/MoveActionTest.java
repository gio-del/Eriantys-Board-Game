package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Entrance;
import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.place.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoveActionTest {
    Place from;
    Place to;

    @BeforeEach
    void setUp() {
        from = new Entrance(7);
        Pawns pawns = new Pawns(1, 1, 1, 1, 1);
        from.add(pawns);
        to = new Island();

    }

    /**
     * Test the move action card when the action is ok
     */
    @Test
    void moveActionIfOk() {
        Pawns pawnsToBeMoved = new Pawns(1, 1, 0, 0, 0);
        assertTrue(new MoveAction(from, to, pawnsToBeMoved).apply());
    }

    /**
     * Test the move action card when the action is not ok
     */
    @Test
    void moveActionIfNotOk() {
        Pawns pawnsToBeMoved = new Pawns(1, 2, 0, 0, 0);
        assertFalse(new MoveAction(from, to, pawnsToBeMoved).apply());
    }
}
