package it.polimi.ingsw.model.school;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.School;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.pawns.PawnColor.*;
import static org.junit.jupiter.api.Assertions.*;


public class SchoolTest {
    private School school1;
    private School schoolInitial;
    private Pawns example;
    private Pawns professorExample;
    private Pawns maxHall;
    private Pawns overMax;

    @BeforeEach
    void setUp() {
        school1 = new School();
        schoolInitial = new School();
        example = new Pawns();
        example.addColor(GREEN,3);
        example.addColor(BLUE,1);
        example.addColor(YELLOW,4);
        professorExample = new Pawns();
        professorExample.addColor(GREEN);
        professorExample.addColor(YELLOW);
        professorExample.addColor(BLUE);
        maxHall = new Pawns();
        maxHall.addColor(GREEN,7);
        maxHall.addColor(RED,7);
        maxHall.addColor(PINK,7);
        maxHall.addColor(YELLOW,7);
        maxHall.addColor(BLUE,7);
        overMax = new Pawns();
        overMax.addColor(GREEN,10);
        overMax.addColor(RED,10);
        overMax.addColor(PINK,10);
        overMax.addColor(YELLOW,10);
        overMax.addColor(BLUE,10);
    }

    @Test
    void initialTest() {
        Pawns pawns = new Pawns();
        assertEquals(pawns,schoolInitial.getEntrance());
        assertEquals(pawns,schoolInitial.getHall());
        assertEquals(pawns,schoolInitial.getProfessorTable());
    }

    @Test
    void addStudentInHallTest() {
        school1.addStudentInHall(example);
        assertEquals(example,school1.getHall());
    }

    @Test
    void addStudentInEntranceTest() {
        assertFalse(school1.addStudentInEntrance(example));
        assertNotEquals(example,school1.getEntrance());
    }

    @Test
    void addProfessorTest() {
        for(PawnColor pawnColor: PawnColor.values()){
            for(int i=0;i<example.getFromColor(pawnColor);i++) {
                school1.addProfessor(pawnColor);
            }
        }
        assertNotEquals(example,school1.getProfessorTable());
        assertEquals(professorExample,school1.getProfessorTable());
    }

    @Test
    void removeProfessorTest() {
        school1.addProfessor(GREEN);
        school1.addProfessor(YELLOW);
        school1.addProfessor(BLUE);
        school1.addProfessor(RED);
        school1.addProfessor(PINK);
        assertNotEquals(professorExample,school1.getProfessorTable());
        school1.removeProfessor(PINK);
        assertNotEquals(professorExample,school1.getProfessorTable());
        school1.removeProfessor(RED);
        assertEquals(professorExample,school1.getProfessorTable());
    }

    @Test
    void removeStudentFromEntranceTest() {
        Pawns empty = new Pawns();
        assertEquals(empty,school1.getEntrance());
        assertFalse(school1.addStudentInEntrance(example));
        school1.removeStudentFromEntrance(example);
        assertEquals(empty,school1.getEntrance());
    }

    @Test
    void removeStudentFromHallTest() {
        Pawns empty = new Pawns();
        assertEquals(empty,school1.getHall());
        school1.addStudentInHall(example);
        assertNotEquals(empty,school1.getHall());
        school1.removeStudentFromHall(example);
        assertEquals(empty,school1.getHall());
    }

    @Test
    void moveTest() {
        Pawns empty = new Pawns();
        assertFalse(school1.addStudentInEntrance(example));
        assertNotEquals(example,school1.getEntrance());
        assertEquals(empty,school1.getHall());
        school1.moveStudentToHall(example);
        assertEquals(empty,school1.getEntrance());
    }

    @Test
    void booleanTest() {
        boolean result;
        result = school1.addStudentInHall(maxHall);
        assertTrue(result);
        result = school1.addStudentInEntrance(overMax);
        assertFalse(result);
        result = school1.removeStudentFromEntrance(overMax);
        assertFalse(result);
        result = school1.removeStudentFromHall(maxHall);
        assertTrue(result);
    }
}
