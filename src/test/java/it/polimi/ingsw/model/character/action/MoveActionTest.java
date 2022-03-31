package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.character.Action;
import it.polimi.ingsw.model.character.MoveAction;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Entrance;
import it.polimi.ingsw.model.place.Hall;
import it.polimi.ingsw.model.place.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveActionTest {
    Place from;
    Place to;

    @BeforeEach
    void setUp() {
        from = new Entrance();
        Pawns pawns = new Pawns();
        pawns.fastSetup(1,1,1,1,1);
        from.add(pawns);
        to = new Hall();

    }

    @Test
    void moveActionIfOk() {
        Pawns pawnsToBeMoved = new Pawns();
        pawnsToBeMoved.fastSetup(1,1,0,0,0);
        assertTrue(new MoveAction(from,to,pawnsToBeMoved).apply());
    }

    @Test
    void moveActionIfNotOk() {
        Pawns pawnsToBeMoved = new Pawns();
        pawnsToBeMoved.fastSetup(1,2,0,0,0);
        assertFalse(new MoveAction(from,to,pawnsToBeMoved).apply());
    }
}
