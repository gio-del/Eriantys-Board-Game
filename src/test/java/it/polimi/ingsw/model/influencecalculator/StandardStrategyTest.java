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
    StandardStrategy standardStrategy;
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
        standardStrategy = new StandardStrategy();
        example1 = new Pawns();
        example1.addColor(GREEN,3);
        example1.addColor(BLUE,2);
        example1.addColor(YELLOW,4);
        player1.getSchool().addStudentInHall(example1);
        player1.getSchool().addProfessor(GREEN);
        player1.getSchool().addProfessor(YELLOW);

        example2 = new Pawns();
        example2.addColor(GREEN,2);
        example2.addColor(BLUE,4);
        example2.addColor(YELLOW,1);
        player2.getSchool().addStudentInHall(example2);
        player2.getSchool().addProfessor(BLUE);

        example3 = new Pawns();
        example3.addColor(GREEN,1);
        example3.addColor(BLUE,1);
        example3.addColor(YELLOW,1);
        player3.getSchool().addStudentInHall(example3);

    }

    @AfterEach
    void tearDown() {
        Game.resetInstance();
    }

    @Test
    void calculateScores(){
        Island island = new Island();
        Pawns exampleIsland = new Pawns();
        exampleIsland.addColor(GREEN,1);
        exampleIsland.addColor(BLUE,3);
        exampleIsland.addColor(YELLOW,1);
        island.add(exampleIsland);
        Map<Player, Integer> score = new HashMap<>();
        score.put(player1, 2);
        score.put(player2, 3);
        assertEquals(score, standardStrategy.getScores(island, game.getPlayers()));
    }


}
