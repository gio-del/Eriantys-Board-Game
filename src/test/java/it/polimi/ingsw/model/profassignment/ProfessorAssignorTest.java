package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.player.TowerColor.*;
import static it.polimi.ingsw.model.player.Wizard.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProfessorAssignorTest {
    Game game;
    Player player1;
    Player player2;
    Player player3;


    @BeforeEach
    void setUp(){
        game = Game.getInstance();
        player1 = new Player("Mario", WIZ1, BLACK);
        player2 = new Player("Albert",WIZ2, WHITE);
        player3 = new Player("Giovanni",WIZ3, GRAY);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);


    }

    @AfterEach
    void tearDown() {
        Game.resetInstance();
    }

    @Test
    void firstProfessorAssignment(){
        player2.getSchool().getHall().fastSetup(1,0,0,0,0);
        assertEquals(player2, game.getProfessorAssignor().colorProfessorChecker(PawnColor.GREEN, game.getPlayers()));
    }

    @Test
    void firsProfessorTie(){
        player1.getSchool().getHall().fastSetup(1,0,0,0,0);
        player2.getSchool().getHall().fastSetup(1,0,0,0,0);
        assertNull(game.getProfessorAssignor().colorProfessorChecker(PawnColor.GREEN, game.getPlayers()));
    }

    @Test
    void moreProfessorsWin(){
        player1.getSchool().getHall().fastSetup(5,0,0,0,0);
        player2.getSchool().getHall().fastSetup(6,0,0,0,0);
        player3.getSchool().getHall().fastSetup(7,0,0,0,0);
        assertEquals(player3, game.getProfessorAssignor().colorProfessorChecker(PawnColor.GREEN, game.getPlayers()));
    }

    @Test
    void moreProfessorsTie(){
        player1.getSchool().getHall().fastSetup(6,0,0,0,0);
        player2.getSchool().getHall().fastSetup(6,0,0,0,0);
        player3.getSchool().getHall().fastSetup(6,0,0,0,0);
        assertNull(game.getProfessorAssignor().colorProfessorChecker(PawnColor.GREEN, game.getPlayers()));
    }

    @Test
    void farmerStrategy(){
        FarmerProfStrategy farmerProfStrategy = new FarmerProfStrategy(player1);
        game.getProfessorAssignor().setProfessorStrategy(farmerProfStrategy);
        player1.getSchool().getHall().fastSetup(6,0,0,0,0);
        player2.getSchool().getHall().fastSetup(6,0,0,0,0);
        player3.getSchool().getHall().fastSetup(6,0,0,0,0);
        assertEquals(player1, game.getProfessorAssignor().colorProfessorChecker(PawnColor.GREEN, game.getPlayers()));
    }

    @Test
    void zeroPawns(){
        assertNull(game.getProfessorAssignor().colorProfessorChecker(PawnColor.GREEN, game.getPlayers()));
    }

    @Test
    void professorsStart(){
        Pawns pawns = new Pawns(1,1,1,1,1);
        assertEquals(pawns,game.getProfessorAssignor().getProfsNotYetAssigned());
    }
}
