package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.pawns.PawnColor.GREEN;
import static it.polimi.ingsw.model.player.TowerColor.*;
import static it.polimi.ingsw.model.player.Wizard.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProfessorAssignorTest {
    Game game;
    Player player1;
    Player player2;
    Player player3;
    ProfessorAssignor professorAssignor;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.addPlayer("Mario", KING, BLACK);
        game.addPlayer("Albert", SORCERER, WHITE);
        game.addPlayer("Giovanni", WITCH, GREY);
        game.init();

        professorAssignor = new ProfessorAssignor();

        player1 = game.getPlayerByName("Mario");
        player2 = game.getPlayerByName("Albert");
        player3 = game.getPlayerByName("Giovanni");
    }

    /**
     * Test the first assignment of a professor
     */
    @Test
    void firstProfessorAssignment() {
        player2.getSchool().addStudentInHall(new Pawns(GREEN));
        assertEquals(1, player2.getSchool().getProfessorTable().getFromColor(GREEN));
    }

    /**
     * Test the case of tie
     */
    @Test
    void firsProfessorTie() {
        player1.getSchool().getHall().addPawns(new Pawns(1, 0, 0, 0, 0));
        player2.getSchool().getHall().addPawns(new Pawns(1, 0, 0, 0, 0));
        assertNull(professorAssignor.colorProfessorChecker(GREEN, game.getPlayers()));
    }

    /**
     * Test the correct assignment of Professor
     */
    @Test
    void moreProfessorsWin() {
        player1.getSchool().getHall().addPawns(new Pawns(5, 0, 0, 0, 0));
        player2.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        player3.getSchool().getHall().addPawns(new Pawns(7, 0, 0, 0, 0));
        assertEquals(player3, professorAssignor.colorProfessorChecker(GREEN, game.getPlayers()));
    }

    /**
     * Test case of tie when 3 player are involved
     */
    @Test
    void moreProfessorsTie() {
        player1.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        player2.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        player3.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        assertNull(professorAssignor.colorProfessorChecker(GREEN, game.getPlayers()));
    }

    /**
     * Test the case of Professor gained
     */
    @Test
    void gainProfessor() {
        player1.getSchool().getHall().addPawns(new Pawns(1, 0, 0, 0, 0));
        assertEquals(player1, professorAssignor.colorProfessorChecker(GREEN, game.getPlayers()));

        player1.getSchool().getHall().addPawns(new Pawns(5, 0, 0, 0, 0));
        player2.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        player3.getSchool().getHall().addPawns(new Pawns(7, 0, 0, 0, 0));
        assertEquals(player3, professorAssignor.colorProfessorChecker(GREEN, game.getPlayers()));
    }

    /**
     * In case of tie, if farmerStrategy is set the player who chose the card to set it wins
     */
    @Test
    void farmerStrategy() {
        FarmerProfStrategy farmerProfStrategy = new FarmerProfStrategy(player1);
        professorAssignor.setProfessorStrategy(farmerProfStrategy);
        player1.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        player2.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        player3.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        assertEquals(player1, professorAssignor.colorProfessorChecker(GREEN, game.getPlayers()));
    }

    /**
     * Test that even with FarmerStrategy activated on Player1 he can lose anyway if his pawn
     * are less than others Player
     */
    @Test
    void farmerStrategyButPlayerLoseAnyway() {
        FarmerProfStrategy farmerProfStrategy = new FarmerProfStrategy(player1);
        professorAssignor.setProfessorStrategy(farmerProfStrategy);
        player1.getSchool().getHall().addPawns(new Pawns(5, 0, 0, 0, 0));
        player2.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        player3.getSchool().getHall().addPawns(new Pawns(6, 0, 0, 0, 0));
        assertNull(professorAssignor.colorProfessorChecker(GREEN, game.getPlayers()));
    }

    /**
     * Test that if no Pawns are present in  each Hall no bugs are present
     */
    @Test
    void zeroPawns() {
        assertNull(professorAssignor.colorProfessorChecker(GREEN, game.getPlayers()));
    }

    /**
     * Test that initially Professor are not assigned, and they are hold by the {@link ProfessorAssignor}
     */
    @Test
    void professorsStart() {
        Pawns pawns = new Pawns(1, 1, 1, 1, 1);
        assertEquals(pawns, professorAssignor.getProfsNotYetAssigned());
    }
}
