package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.pawns.Pawns;

import it.polimi.ingsw.model.player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.player.TowerColor.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static it.polimi.ingsw.model.pawns.PawnColor.*;
import static it.polimi.ingsw.model.player.Wizard.KING;
import static it.polimi.ingsw.model.player.Wizard.SORCERER;

/**
 * This test class is used to test the standard strategy of the influence calc
 */
class StandardStrategyTest {
    private Player player1;
    private Player player2;
    private List<Player> players;

    @BeforeEach
    void setUp(){
        Game game = new Game(2,false);
        game.addPlayer("Mario", KING, BLACK);
        game.addPlayer("Lorenzo", SORCERER, WHITE);
        player1 = game.getPlayerByName("Mario");
        player2 = game.getPlayerByName("Lorenzo");

        Pawns example1 = new Pawns(3, 0, 4, 0, 2);
        player1.getSchool().getHall().addPawns(example1);
        player1.getSchool().addProfessor(GREEN);
        player1.getSchool().addProfessor(YELLOW);

        Pawns example2 = new Pawns(2, 0, 1, 0, 4);
        player2.getSchool().getHall().addPawns(example2);
        player2.getSchool().addProfessor(BLUE);

        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
    }

    /**
     * Test that influence scores are calculated correctly (in this case Standard Strategy is used)
     * On the island there are 1 green, 1 yellow, 3 blue
     * Player 1 controls Green and Yellow professors so its influence is 2
     * Player 2 controls Blue professor so its influence is 3
     */
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
