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

    @Test
    void addStudentPawns(){
        island.add(exampleIsland);
        assertEquals(exampleIsland, island.getStudents());
    }

    @Test
    void addStudentColor(){
        island.add(GREEN);
        Pawns exampleIsland2 = new Pawns();
        exampleIsland2.addColor(GREEN,1);
        assertEquals(exampleIsland2, island.getStudents());
    }


}
