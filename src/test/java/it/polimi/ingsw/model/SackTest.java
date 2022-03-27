package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SackTest {
    Sack sack;

    @BeforeEach
    void setUp() {
        sack = new Sack();
        sack.initialFill();
    }

    @Test
    void extractTest() {
        sack.extract();
        assertEquals(Constants.NumOfStudentsOfEachColor*PawnColor.values().length-1,sack.getNumberOfPawns());
    }

    @Test
    void extractAll_checkEmptiness(){
        for(int i = 0; i<Constants.NumOfStudentsOfEachColor*PawnColor.values().length;i++){
            sack.extract();
        }
        assertEquals(0, sack.getNumberOfPawns());
    }

    @Test
    void extractListOfPawnsTest(){
        assertEquals(5, sack.extractListOfPawns(5).totalElements());
    }

    @Test
    void extractListOfPawnsTest_checkEmptiness(){
        sack.extractListOfPawns(Constants.NumOfStudentsOfEachColor*PawnColor.values().length);
        assertEquals(0,sack.getNumberOfPawns());
    }
}
