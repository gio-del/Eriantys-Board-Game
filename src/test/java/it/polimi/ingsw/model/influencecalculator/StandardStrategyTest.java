package it.polimi.ingsw.model.influencecalculator;


import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawns.Pawns;

import it.polimi.ingsw.model.player.Player;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.player.TowerColor.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static it.polimi.ingsw.model.pawns.PawnColor.*;
import static it.polimi.ingsw.model.player.Wizard.WIZ1;
import static it.polimi.ingsw.model.player.Wizard.WIZ2;

public class StandardStrategyTest {
    Player player1;
    Player player2;
    Player player3;
    Game game;
    Pawns example1;
    Pawns example2;
    Pawns example3;


    @BeforeEach
    void setUp(){
        game = Game.getInstance();
        player1 = new Player("Mario", WIZ1, BLACK);
        player2 = new Player("Lorenzo",WIZ2, WHITE);
        player3 = new Player("Lorenzo",WIZ2, GRAY);

        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);

        example1 = new Pawns(3,0,4,0,2);
        player1.getSchool().addStudentInHall(example1);
        player1.getSchool().addProfessor(GREEN);
        player1.getSchool().addProfessor(YELLOW);

        example2 = new Pawns(2,0,1,0,4);
        player2.getSchool().addStudentInHall(example2);
        player2.getSchool().addProfessor(BLUE);

        example3 = new Pawns(1,0,1,0,1);
        player3.getSchool().addStudentInHall(example3);

    }

    @AfterEach
    void tearDown() {
        Game.resetInstance();
    }

    @Test
    void calculateScoresTest(){
        Island island = new Island();
        Pawns exampleIsland = new Pawns(1,0,1,0,3);
        island.add(exampleIsland);
        Map<Player, Integer> score = new HashMap<>();
        score.put(player1, 2);
        score.put(player2, 3);
        assertEquals(score, new StandardStrategy().getScores(island, game.getPlayers()));
    }

}
