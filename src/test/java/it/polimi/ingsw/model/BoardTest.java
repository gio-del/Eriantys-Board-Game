package it.polimi.ingsw.model;

import it.polimi.ingsw.model.influencecalculator.*;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.player.Wizard.*;
import static org.junit.jupiter.api.Assertions.*;

import static it.polimi.ingsw.model.pawns.PawnColor.*;
import static it.polimi.ingsw.model.pawns.PawnColor.YELLOW;
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
        game = Game.getInstance();
        player1 = new Player("Mario", WIZ1, BLACK);
        player2 = new Player("Lorenzo", WIZ2, WHITE);
        player3 = new Player("Giovanni", WIZ3, GRAY);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);

        example1 = new Pawns();
        example1.fastSetup(3,0,4,0,2);
        player1.getSchool().addStudentInHall(example1);
        player1.getSchool().addProfessor(GREEN);
        player1.getSchool().addProfessor(YELLOW);

        example2 = new Pawns();
        example2.fastSetup(2,0,1,0,4);
        player2.getSchool().addStudentInHall(example2);
        player2.getSchool().addProfessor(BLUE);

        example3 = new Pawns();
        example3.fastSetup(1,0,1,0,1);
        player3.getSchool().addStudentInHall(example3);


        island = game.getBoard().getIslands().get(4);
        Pawns exampleIsland = new Pawns();
        exampleIsland.fastSetup(0,0,1,0,3);
        island.add(exampleIsland);

        island = game.getBoard().getIslands().get(1);
        Pawns exampleIsland2 = new Pawns();
        exampleIsland2.fastSetup(1,0,1,0,3);
        island.add(exampleIsland2);
    }

    @AfterEach
    void tearDown() {
        Game.resetInstance();
    }

    @Test
    void numberOfIslands(){
        assertEquals(12, game.getBoard().getIslands().size());
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
}
