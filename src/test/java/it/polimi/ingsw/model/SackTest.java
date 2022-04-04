package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the {@link Sack} methods.
 */
public class SackTest {
    Sack sack1;
    Sack sack2;

    @BeforeEach
    void setUp() {
        sack1 = new Sack();
        sack2 = new Sack();
        sack1.initialFill();
        sack2.fill();
    }

    /**
     * This method tests initialFill and extract.
     */
    @Test
    void extractTest() {
        sack1.extract();
        assertEquals(Constants.INITIAL_NUM_OF_STUDENTS_OF_EACH_COLOR *PawnColor.values().length-1,sack1.getNumberOfPawns());
    }

    /**
     * This method tests extract and getNumberOfPawns, check the emptiness of sack after the extraction of the max_number of pawns for each {@link PawnColor}.
     */
    @Test
    void extractAll_checkEmptiness(){
        for(int i = 0; i<Constants.NUM_OF_STUDENTS_OF_EACH_COLOR *PawnColor.values().length; i++){
            sack2.extract();
        }
        assertEquals(0, sack2.getNumberOfPawns());
    }

    /**
     * This method tests extractListOfPawns.
     */
    @Test
    void extractListOfPawnsTest(){
        assertEquals(5, sack1.extractListOfPawns(5).totalElements());
    }

    /**
     * This method tests extractListOfPawns and getNumberOfPawns, check the emptiness of sack after the extraction of the max_number of pawns for each {@link PawnColor}.
     */
    @Test
    void extractListOfPawnsTest_checkEmptiness(){
        sack2.extractListOfPawns(Constants.NUM_OF_STUDENTS_OF_EACH_COLOR *PawnColor.values().length);
        assertEquals(0,sack2.getNumberOfPawns());
    }
}