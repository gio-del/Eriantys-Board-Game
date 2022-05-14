package it.polimi.ingsw.model.place;

import it.polimi.ingsw.utility.gamelimit.GameLimit;
import it.polimi.ingsw.model.Bank;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.pawns.PawnColor.*;
import static org.junit.jupiter.api.Assertions.*;


class SchoolTest {
    private School school;
    private Pawns example;
    private Pawns professorExample;
    private Pawns maxHall;
    private Pawns overMax;

    @BeforeEach
    void setUp() {
        HallManager hallManager = new HallManager(new Bank());
        Player player = new Player("Fausto", Wizard.KING, TowerColor.BLACK, GameLimit.getLimit(2), hallManager);
        hallManager.addPlayer(player);

        school = player.getSchool();
        example = new Pawns(3,0,4,0,1);
        professorExample = new Pawns(1,0,1,0,1);
        maxHall = new Pawns(10,10,10,10,10);
        overMax = new Pawns(10,10,10,10,11);
    }

    /**
     * Test that initially a {@link School} is empty
     */
    @Test
    void initialTest() {
        Pawns pawns = new Pawns();
        assertEquals(pawns,school.getEntrance());
        assertEquals(pawns,school.getHall());
        assertEquals(pawns,school.getProfessorTable());
    }

    /**
     * Test that add student in hall works
     */
    @Test
    void addStudentInHallIfOKTest() {
        school.addStudentInHall(example);
        assertEquals(example, school.getHall());
    }

    /**
     * Test that add student in hall fail if the pawn exceed the limit of the Hall
     */
    @Test
    void addStudentInHallIfKOTest() {
        assertFalse(school.addStudentInHall(overMax));
        assertEquals(new Pawns(),school.getHall());
    }

    /**
     * Test that add student in entrance works
     */
    @Test
    void addStudentInEntranceIfOKTest() {
        example.removeColor(BLUE);
        assertTrue(school.addStudentInEntrance(example));
        assertEquals(example, school.getEntrance());
    }

    /**
     * Test that add student in Entrance doesn't work if pawn exceed the limit of the entrance
     */
    @Test
    void addStudentInEntranceIfKOTest() {
        assertFalse(school.addStudentInEntrance(example));
        assertNotEquals(example, school.getEntrance());
    }

    /**
     * Test that add professor to Prof Table works
     */
    @Test
    void addProfessorTest() {
        for(PawnColor pawnColor: PawnColor.values()){
            for(int i=0;i<example.getFromColor(pawnColor);i++) {
                school.addProfessor(pawnColor);
            }
        }
        assertNotEquals(example, school.getProfessorTable());
        assertEquals(professorExample, school.getProfessorTable());
    }

    /**
     * Test that a professor can be removed correctly
     */
    @Test
    void removeProfessorTest() {
        school.addProfessor(GREEN);
        school.addProfessor(YELLOW);
        school.addProfessor(BLUE);
        school.addProfessor(RED);
        school.addProfessor(PINK);
        assertNotEquals(professorExample, school.getProfessorTable());
        school.removeProfessor(PINK);
        assertNotEquals(professorExample, school.getProfessorTable());
        school.removeProfessor(RED);
        assertEquals(professorExample, school.getProfessorTable());
    }

    /**
     * Test that a student can be removed from entrance
     */
    @Test
    void removeStudentFromEntranceTest() {
        Pawns empty = new Pawns();
        assertEquals(empty, school.getEntrance());
        assertFalse(school.addStudentInEntrance(example));
        school.removeStudentFromEntrance(example);
        assertEquals(empty, school.getEntrance());
    }

    /**
     * Test that student can be removed from hall correctly
     */
    @Test
    void removeStudentFromHallTest() {
        Pawns empty = new Pawns();
        assertEquals(empty, school.getHall());
        school.getHall().addPawns(example);
        assertNotEquals(empty, school.getHall());
        school.removeStudentFromHall(example);
        assertEquals(empty, school.getHall());
    }

    /**
     * Test that pawn can be moved from entrance to hall
     */
    @Test
    void moveIfOKTest() {
        Pawns entrancePawns = new Pawns(1,2,1,2,1);
        school.addStudentInEntrance(entrancePawns);
        school.getHall().addPawns(example);
        school.moveStudentToHall(new Pawns(GREEN));
        assertEquals(new Pawns(0,2,1,2,1),school.getEntrance());
        assertEquals(new Pawns(4,0,4,0,1), school.getHall());
    }

    /**
     * Test that pawn cannot be moved from entrance to hall if they exceed the limit of hall or entrance
     */
    @Test
    void moveIfKOTest() {
        Pawns entrancePawns = new Pawns(1,2,1,2,1);
        school.addStudentInEntrance(entrancePawns);
        school.getHall().addPawns(example);
        school.moveStudentToHall(new Pawns(2,0,0,0,0));
        assertEquals(new Pawns(1,2,1,2,1),school.getEntrance());
        assertEquals(example, school.getHall());
    }

    @Test
    void booleanTest() {
        boolean result;
        result = school.getHallAsPlace().canBeAdded(maxHall);
        assertTrue(result);
        result = school.getEntranceAsPlace().canBeAdded(overMax);
        assertFalse(result);
        result = school.getEntranceAsPlace().canBeRemoved(overMax);
        assertFalse(result);
        school.getHall().addPawns(maxHall);
        result = school.getHallAsPlace().canBeRemoved(maxHall);
        assertTrue(result);
    }
}
