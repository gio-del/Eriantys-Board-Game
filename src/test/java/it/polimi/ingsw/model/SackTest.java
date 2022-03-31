package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void extractTest() {
        sack1.extract();
        assertEquals(Constants.InitialNumOfStudentsOfEachColor*PawnColor.values().length-1,sack1.getNumberOfPawns());
    }

    @Test
    void extractAll_checkEmptiness(){
        for(int i = 0; i<Constants.NumOfStudentsOfEachColor*PawnColor.values().length;i++){
            sack2.extract();
        }
        assertEquals(0, sack2.getNumberOfPawns());
    }

    @Test
    void extractListOfPawnsTest(){
        assertEquals(5, sack1.extractListOfPawns(5).totalElements());
    }

    @Test
    void extractListOfPawnsTest_checkEmptiness(){
        sack2.extractListOfPawns(Constants.NumOfStudentsOfEachColor*PawnColor.values().length);
        assertEquals(0,sack2.getNumberOfPawns());
    }
}
