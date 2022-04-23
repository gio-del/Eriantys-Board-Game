package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.pawns.PawnColor.GREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HallManagerTest {
    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp() {
        Game game = new Game(2,false);

        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        p1 = game.getPlayerByName("Luca");
        p1.getSchool().getEntrance().addPawns(new Pawns(3,0,0,0,0));

        game.addPlayer("Marco", Wizard.SORCERER, TowerColor.WHITE);
        p2 = game.getPlayerByName("Marco");
        p2.getSchool().getHall().addPawns(new Pawns(2,0,0,0,0));
        p2.getSchool().getProfessorTable().addColor(GREEN);

        game.getProfessorAssignor().getProfsNotYetAssigned().removeColor(GREEN);
    }

    /**
     * Test that the Professor Assignor is triggered everytime that a Pawn is moved onto a
     * {@link it.polimi.ingsw.model.place.School} {@link it.polimi.ingsw.model.place.Hall} and that
     * the result of the assignment is correct
     */
    @Test
    void ProfessorAssignmentTriggerTest() {
        assertTrue(p1.moveFromEntranceToHall(new Pawns(GREEN)));
        assertEquals(1,p2.getSchool().getProfessorTable().getFromColor(GREEN));
        assertEquals(0,p1.getSchool().getProfessorTable().getFromColor(GREEN));

        assertTrue(p1.moveFromEntranceToHall(new Pawns(GREEN)));
        assertEquals(1,p2.getSchool().getProfessorTable().getFromColor(GREEN));
        assertEquals(0,p1.getSchool().getProfessorTable().getFromColor(GREEN));


        assertTrue(p1.moveFromEntranceToHall(new Pawns(GREEN)));
        assertEquals(0,p2.getSchool().getProfessorTable().getFromColor(GREEN));
        assertEquals(1,p1.getSchool().getProfessorTable().getFromColor(GREEN));
    }
}
