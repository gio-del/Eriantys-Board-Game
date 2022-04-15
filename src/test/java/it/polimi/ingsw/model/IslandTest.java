package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.Pawns;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static it.polimi.ingsw.model.pawns.PawnColor.*;


public class IslandTest {
    Island island;
    Pawns exampleIsland;
    @BeforeEach
    void setUp(){
        island = new Island();
        exampleIsland = new Pawns();
        exampleIsland.addColor(GREEN,1);
        exampleIsland.addColor(BLUE,3);
        exampleIsland.addColor(YELLOW,1);
    }

    /**
     * add student pawns in island, it can always be done since island has no limit
     */
    @Test
    void addStudentPawns(){
        island.add(exampleIsland);
        assertEquals(exampleIsland, island.getStudents());
    }

    /**
     * test add(Color) method
     */
    @Test
    void addStudentColor(){
        island.add(GREEN);
        assertEquals(new Pawns(GREEN), island.getStudents());
    }

    @Test
    void youCannotRemoveFromIslandTest() {
        assertFalse(island.remove(new Pawns()) || island.canBeRemoved(new Pawns()));
    }
}
