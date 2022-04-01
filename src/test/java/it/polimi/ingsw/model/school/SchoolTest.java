package it.polimi.ingsw.model.school;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.School;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.pawns.PawnColor.*;
import static org.junit.jupiter.api.Assertions.*;


public class SchoolTest {
    private School school;
    private Pawns example;
    private Pawns professorExample;
    private Pawns maxHall;
    private Pawns overMax;

    @BeforeEach
    void setUp() {
        school = new School();
        example = new Pawns(3,0,4,0,1);
        professorExample = new Pawns(1,0,1,0,1);
        maxHall = new Pawns(10,10,10,10,10);
        overMax = new Pawns(10,10,10,10,11);
    }

    @Test
    void initialTest() {
        School schoolInitial = new School();
        Pawns pawns = new Pawns();
        assertEquals(pawns,schoolInitial.getEntrance());
        assertEquals(pawns,schoolInitial.getHall());
        assertEquals(pawns,schoolInitial.getProfessorTable());
    }

    @Test
    void addStudentInHallIfOKTest() {
        school.addStudentInHall(example);
        assertEquals(example, school.getHall());
    }

    @Test
    void addStudentInHallIfKOTest() {
        assertFalse(school.addStudentInHall(overMax));
        assertEquals(new Pawns(),school.getHall());
    }

    @Test
    void addStudentInEntranceIfOKTest() {
        example.removeColor(BLUE);
        assertTrue(school.addStudentInEntrance(example));
        assertEquals(example, school.getEntrance());
    }

    @Test
    void addStudentInEntranceIfKOTest() {
        assertFalse(school.addStudentInEntrance(example));
        assertNotEquals(example, school.getEntrance());
    }

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

    @Test
    void removeStudentFromEntranceTest() {
        Pawns empty = new Pawns();
        assertEquals(empty, school.getEntrance());
        assertFalse(school.addStudentInEntrance(example));
        school.removeStudentFromEntrance(example);
        assertEquals(empty, school.getEntrance());
    }

    @Test
    void removeStudentFromHallTest() {
        Pawns empty = new Pawns();
        assertEquals(empty, school.getHall());
        school.addStudentInHall(example);
        assertNotEquals(empty, school.getHall());
        school.removeStudentFromHall(example);
        assertEquals(empty, school.getHall());
    }

    @Test
    void moveIfOKTest() {
        Pawns entrancePawns = new Pawns(1,2,1,2,1);
        school.addStudentInEntrance(entrancePawns);
        school.addStudentInHall(example);
        school.moveStudentToHall(new Pawns(GREEN));
        assertEquals(new Pawns(0,2,1,2,1),school.getEntrance());
        assertEquals(new Pawns(4,0,4,0,1), school.getHall());
    }

    @Test
    void moveIfKOTest() {
        Pawns entrancePawns = new Pawns(1,2,1,2,1);
        school.addStudentInEntrance(entrancePawns);
        school.addStudentInHall(example);
        school.moveStudentToHall(new Pawns(2,0,0,0,0));
        assertEquals(new Pawns(1,2,1,2,1),school.getEntrance());
        assertEquals(example, school.getHall());
    }

    @Test
    void booleanTest() {
        boolean result;
        result = school.addStudentInHall(maxHall);
        assertTrue(result);
        result = school.addStudentInEntrance(overMax);
        assertFalse(result);
        result = school.removeStudentFromEntrance(overMax);
        assertFalse(result);
        result = school.removeStudentFromHall(maxHall);
        assertTrue(result);
    }
}
