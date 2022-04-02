package it.polimi.ingsw.model.pawns;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the {@link Pawns} methods.
 */
public class PawnsTest {
    Pawns pawns;

    @BeforeEach
    void setUp() {
        pawns = new Pawns(3,7,6,2,5);
    }

    /**
     * This method tests getFromColor.
     */
    @Test
    void getFromColorTest() {
        assertEquals(5,pawns.getFromColor(PawnColor.BLUE));
        assertEquals(6,pawns.getFromColor(PawnColor.YELLOW));
        assertEquals(3,pawns.getFromColor(PawnColor.GREEN));
        assertEquals(7,pawns.getFromColor(PawnColor.RED));
        assertEquals(2,pawns.getFromColor(PawnColor.PINK));
    }

    /**
     * This method tests totalElements.
     */
    @Test
    void totalElementsTest() {
        assertEquals(23,pawns.totalElements());
    }

    /**
     * This method tests removeColor.
     */
    @Test
    void removeColorTest() {
        pawns.removeColor(PawnColor.BLUE);
        assertEquals(4,pawns.getFromColor(PawnColor.BLUE));
    }

    /**
     * This method tests addColor and addPawns.
     */
    @Test
    void addPawnsTest() {
        Pawns p2 = new Pawns();
        p2.addColor(PawnColor.BLUE,2);
        p2.addColor(PawnColor.YELLOW,3);
        p2.addColor(PawnColor.GREEN,5);
        p2.addColor(PawnColor.RED,1);
        p2.addColor(PawnColor.PINK,6);
        pawns.addPawns(p2);
        assertEquals(7,pawns.getFromColor(PawnColor.BLUE));
        assertEquals(9,pawns.getFromColor(PawnColor.YELLOW));
        assertEquals(8,pawns.getFromColor(PawnColor.GREEN));
        assertEquals(8,pawns.getFromColor(PawnColor.RED));
        assertEquals(8,pawns.getFromColor(PawnColor.PINK));
    }
}