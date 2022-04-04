package it.polimi.ingsw.model;

import it.polimi.ingsw.model.influencecalculator.*;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.HallObserver;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.player.Wizard.*;
import static org.junit.jupiter.api.Assertions.*;

import static it.polimi.ingsw.model.pawns.PawnColor.*;
import static it.polimi.ingsw.model.player.TowerColor.*;

public class BoardTest {
    Player player1;
    Player player2;
    Player player3;
    Game game;
    Pawns example1;
    Pawns example2;
    Pawns example3;
    Island island;
    MushroomSellerStrategy mushroomSellerStrategy;
    CentaurStrategy centaurStrategy;
    KnightStrategy knightStrategy;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player("Mario", WIZ1, BLACK);
        player2 = new Player("Lorenzo", WIZ2, WHITE);
        player3 = new Player("Giovanni", WIZ3, GRAY);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);

        example1 = new Pawns(3,0,4,0,2);
        player1.getSchool().getHall().addPawns(example1);
        player1.getSchool().addProfessor(GREEN);
        player1.getSchool().addProfessor(YELLOW);

        example2 = new Pawns(2,0,1,0,4);
        player2.getSchool().getHall().addPawns(example2);
        player2.getSchool().addProfessor(BLUE);

        example3 = new Pawns(1,0,1,0,1);
        player3.getSchool().getHall().addPawns(example3);


        island = game.getBoard().getIslands().get(4);
        Pawns exampleIsland = new Pawns(0,0,1,0,3);
        island.add(exampleIsland);

        island = game.getBoard().getIslands().get(1);
        Pawns exampleIsland2 = new Pawns(1,0,1,0,3);
        island.add(exampleIsland2);
    }

    @AfterEach
    void tearDown() {
        HallObserver.resetInstance();
    }

    @Test
    void numberOfIslands(){
        assertEquals(12, game.getBoard().numberOfIslands());
    }

    @Test
    void checkStepsMotherNature(){
        game.getBoard().setMotherNaturePos(2);
        assertEquals(2, game.getBoard().getMotherNaturePos());
    }

    @Test
    void conquerIslandWithoutTower(){
        game.getBoard().setMotherNaturePos(2);
        assertEquals(TowerColor.WHITE, game.getBoard().moveMotherNature(2, game.getPlayers()));
    }

    @Test
    void conquerIslandWithTowerOfTheWinner(){
        game.getBoard().setMotherNaturePos(2);
        Island island = game.getBoard().getIslands().get(4);
        island.addTower(WHITE);
        assertNull(game.getBoard().moveMotherNature(2, game.getPlayers()));
    }

    @Test
    void conquerIslandWithTowerOfTheLoser(){
        game.getBoard().setMotherNaturePos(2);
        Island island = game.getBoard().getIslands().get(4);
        island.addTower(BLACK);
        assertEquals(WHITE, game.getBoard().moveMotherNature(2, game.getPlayers()));
    }

    @Test
    void tieOnIslandWithTower(){
        game.getBoard().setMotherNaturePos(3);
        Island island = game.getBoard().getIslands().get(5);
        island.addTower(BLACK);
        assertNull(game.getBoard().moveMotherNature(2, game.getPlayers()));
    }

    @Test
    void motherNatureMovementInsideSize(){
        game.getBoard().setMotherNaturePos(3);
        game.getBoard().moveMotherNature(2, game.getPlayers());
        assertEquals(5, game.getBoard().getMotherNaturePos());
    }

    @Test
    void motherNatureMovementLoopArray(){
        game.getBoard().setMotherNaturePos(9);
        game.getBoard().moveMotherNature(4, game.getPlayers());
        assertEquals(1, game.getBoard().getMotherNaturePos());
    }

    @Test
    void mushroomSellerStrategy(){
        mushroomSellerStrategy = new MushroomSellerStrategy();
        mushroomSellerStrategy.setBlockedColor(GREEN);
        game.getBoard().setStrategy(mushroomSellerStrategy);
        game.getBoard().setMotherNaturePos(10);
        assertEquals(WHITE, game.getBoard().moveMotherNature(3, game.getPlayers()));
    }

    @Test
    void centaurStrategy(){
        centaurStrategy = new CentaurStrategy();
        game.getBoard().setStrategy(centaurStrategy);
        game.getBoard().setMotherNaturePos(10);
        Island island = game.getBoard().getIslands().get(1);
        island.addTower(BLACK);
        assertEquals(WHITE, game.getBoard().moveMotherNature(3, game.getPlayers()));
    }

    @Test
    void KnightStrategy(){
        knightStrategy = new KnightStrategy(BLACK);
        game.getBoard().setStrategy(knightStrategy);
        game.getBoard().setMotherNaturePos(10);
        assertEquals(BLACK, game.getBoard().moveMotherNature(3, game.getPlayers()));
    }

    @Test
    void twoIslandsMergeMiddle(){
        Board board2 = new Board();
        board2.getIslands().get(0).addTower(BLACK);
        board2.getIslands().get(1).addTower(BLACK);
        board2.getIslands().get(2).addTower(WHITE);

        board2.adjacenciesUpdate();

        assertEquals(11, board2.getIslands().size());
    }

    /**
     * Testing the case of three adjacent islands with the same TowerColor
     * Case with the Last two and the first of the ArrayList with the same TowerColor
     */
    @Test
    void threeIslandsLastThenFirst(){
        Board board2 = new Board();
        board2.getIslands().get(10).addTower(BLACK);
        board2.getIslands().get(11).addTower(BLACK);
        board2.getIslands().get(0).addTower(BLACK);

        board2.adjacenciesUpdate();
        assertEquals(10, board2.getIslands().size());

    }

    /**
     * Testing the case of three adjacent islands with the same TowerColor
     * Case with three Islands adjacent in the middle of the ArrayList
     */
    @Test
    void threeIslandsMiddle(){
        Board board2 = new Board();
        board2.getIslands().get(5).addTower(BLACK);
        board2.getIslands().get(6).addTower(BLACK);
        board2.getIslands().get(7).addTower(BLACK);

        board2.adjacenciesUpdate();
        assertEquals(10, board2.getIslands().size());

    }

    /**
     * Testing the act by the WHITE of conquering an Island with a BLACK tower on it
     * Before moving motherNature: island(3): WHITE | island(4): BLACK | island(5): WHITE
     * After moving motherNature: island(3): WHITE (of dimension of 3)
     */
    @Test
    void conquerIslandAndTriggerAdjancies(){
        game.getBoard().setMotherNaturePos(2);
        Island island = game.getBoard().getIslands().get(4);
        island.addTower(BLACK);
        game.getBoard().getIslands().get(3).addTower(WHITE);
        game.getBoard().getIslands().get(5).addTower(WHITE);
        game.getBoard().moveMotherNature(2, game.getPlayers());
        assertEquals(10, game.getBoard().getIslands().size());
    }

    /**
     * Testing the sum of the Pawns during an adjancesUpdate
     * 3 adjacent islands with the same tower updating to 1 island with
     * the sum of all the previous pawns
     */

    @Test
    void sumOfPawns(){
        game.getBoard().setMotherNaturePos(2);
        Island island = game.getBoard().getIslands().get(4);
        island.addTower(BLACK);

        Island island3 = game.getBoard().getIslands().get(3);
        Pawns exampleIsland3 = new Pawns();
        exampleIsland3.addPawns(new Pawns(1,3,5,2,3));
        island3.add(exampleIsland3);
        island3.addTower(WHITE);


        Island island5 = game.getBoard().getIslands().get(5);
        Pawns exampleIsland5 = new Pawns();
        exampleIsland5.addPawns(new Pawns(3,1,4,1,6));
        island5.add(exampleIsland5);
        island5.addTower(WHITE);
        game.getBoard().moveMotherNature(2, game.getPlayers());

        assertEquals(4, game.getBoard().getIslands().get(3).getStudents().getFromColor(GREEN));
        assertEquals(4, game.getBoard().getIslands().get(3).getStudents().getFromColor(RED));
        assertEquals(10, game.getBoard().getIslands().get(3).getStudents().getFromColor(YELLOW));
        assertEquals(3, game.getBoard().getIslands().get(3).getStudents().getFromColor(PINK));
        assertEquals(12, game.getBoard().getIslands().get(3).getStudents().getFromColor(BLUE));
    }


}
