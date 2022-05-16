package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Bank;
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
    private Bank bank;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Marco", Wizard.SORCERER, TowerColor.WHITE);
        game.init();

        p1 = game.getPlayerByName("Luca");
        p1.getSchool().getEntrance().addPawns(new Pawns(4, 0, 0, 0, 0));

        p2 = game.getPlayerByName("Marco");
        p2.getSchool().getHall().addPawns(new Pawns(2, 0, 0, 0, 0));
        p2.getSchool().getProfessorTable().addColor(GREEN);

        game.getProfessorAssignor().getProfsNotYetAssigned().removeColor(GREEN);

        bank = game.getBank();
    }

    /**
     * Test that the Professor Assignor is triggered everytime that a Pawn is moved onto a
     * {@link it.polimi.ingsw.model.place.School} {@link it.polimi.ingsw.model.place.Hall} and that
     * the result of the assignment is correct
     */
    @Test
    void ProfessorAssignmentTriggerTest() {
        assertTrue(p1.moveFromEntranceToHall(new Pawns(GREEN)));
        assertEquals(1, p2.getSchool().getProfessorTable().getFromColor(GREEN));
        assertEquals(0, p1.getSchool().getProfessorTable().getFromColor(GREEN));

        assertTrue(p1.moveFromEntranceToHall(new Pawns(GREEN)));
        assertEquals(1, p2.getSchool().getProfessorTable().getFromColor(GREEN));
        assertEquals(0, p1.getSchool().getProfessorTable().getFromColor(GREEN));


        assertTrue(p1.moveFromEntranceToHall(new Pawns(GREEN)));
        assertEquals(0, p2.getSchool().getProfessorTable().getFromColor(GREEN));
        assertEquals(1, p1.getSchool().getProfessorTable().getFromColor(GREEN));
    }

    @Test
    void initialMoneyTest() {
        assertEquals(Constants.MAX_NUM_OF_COINS - 2, bank.getGeneralBank());
        assertEquals(1, bank.getCashByPlayer(p1));
        assertEquals(1, bank.getCashByPlayer(p2));
    }

    @Test
    void CheckMoneyTriggeredTest() {
        p1.moveFromEntranceToHall(new Pawns(GREEN));
        assertEquals(1, bank.getCashByPlayer(p1));

        p1.moveFromEntranceToHall(new Pawns(GREEN));
        assertEquals(1, bank.getCashByPlayer(p1));

        p1.moveFromEntranceToHall(new Pawns(GREEN));
        assertEquals(2, bank.getCashByPlayer(p1));
        assertEquals(17, bank.getGeneralBank());
    }

    @Test
    void CheckIfMorePawnsAreMoved() {
        Pawns pawns = new Pawns(4, 0, 0, 0, 0);
        p1.moveFromEntranceToHall(pawns);
        assertEquals(2, bank.getCashByPlayer(p1));
        assertEquals(17, bank.getGeneralBank());
    }
}
