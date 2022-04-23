package it.polimi.ingsw.model.clouds;

import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the {@link Cloud} methods.
 */
class CloudTest {
    Cloud cloud;
    Pawns pawns;

    @BeforeEach
    void setUp() {
        cloud = new Cloud();
        pawns = new Pawns();

        pawns.addColor(PawnColor.BLUE,4);
        pawns.addColor(PawnColor.PINK,7);
        pawns.addColor(PawnColor.RED,2);
        pawns.addColor(PawnColor.YELLOW,1);
        pawns.addColor(PawnColor.GREEN,8);

        cloud.fill(pawns);
    }

    /**
     * This method tests getStudentAndRemove.
     */
    @Test
    void getStudentsAndRemoveTest() {
        assertEquals(pawns,cloud.getStudentsAndRemove());
        assertEquals(0,cloud.getStudents().totalElements());
    }

    /**
     * This method tests getStudents.
     */
    @Test
    void getStudentsTest() {
        assertEquals(pawns,cloud.getStudents());
        assertEquals(22,cloud.getStudents().totalElements());
    }
}
