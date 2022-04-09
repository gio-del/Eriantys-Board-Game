package it.polimi.ingsw.model.influencecalculator;


import it.polimi.ingsw.model.GameLimit;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawns.Pawns;

import it.polimi.ingsw.model.place.HallObserver;
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

/**
 * This test class is used to test the standard strategy of the influence calc
 */
public class StandardStrategyTest {
    Player player1;
    Player player2;
    List<Player> players;
    Pawns example1;
    Pawns example2;


    @BeforeEach
    void setUp(){
        GameLimit gameLimit = new GameLimit(false);
        player1 = new Player("Mario", WIZ1, BLACK,gameLimit);
        player2 = new Player("Lorenzo",WIZ2, WHITE,gameLimit);

        example1 = new Pawns(3,0,4,0,2);
        player1.getSchool().getHall().addPawns(example1);
        player1.getSchool().addProfessor(GREEN);
        player1.getSchool().addProfessor(YELLOW);

        example2 = new Pawns(2,0,1,0,4);
        player2.getSchool().getHall().addPawns(example2);
        player2.getSchool().addProfessor(BLUE);

        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
    }

    @AfterEach
    void tearDown() {
        HallObserver.resetInstance();
    }

    @Test
    void calculateScoresTest(){
        Island island = new Island();
        Pawns exampleIsland = new Pawns(1,0,1,0,3);
        island.add(exampleIsland);
        Map<Player, Integer> score = new HashMap<>();
        score.put(player1, 2);
        score.put(player2, 3);
        assertEquals(score, new StandardStrategy().getScores(island, players));
    }

}
