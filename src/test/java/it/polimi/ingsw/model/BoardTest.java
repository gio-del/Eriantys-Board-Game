package it.polimi.ingsw.model;

import it.polimi.ingsw.model.influencecalculator.*;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.constants.Constants.MAX_ISLAND;
import static it.polimi.ingsw.model.player.Wizard.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import static it.polimi.ingsw.model.pawns.PawnColor.*;
import static it.polimi.ingsw.model.pawns.PawnColor.YELLOW;
import static it.polimi.ingsw.model.player.TowerColor.*;

public class BoardTest {
    Board initialBoard;
    Sack initialSack;
    Player player1;
    Player player2;
    Player player3;
    Game game;
    StandardStrategy standardStrategy;
    Pawns example1;
    Pawns example2;
    Pawns example3;
    //Island island1;
    //Island island2;
    MushroomSellerStrategy mushroomSellerStrategy;
    CentaurStrategy centaurStrategy;
    KnightStrategy knightStrategy;

    @BeforeEach
    void setUp(){
        game = Game.getInstance();
        player1 = new Player("Mario", WIZ1, BLACK);
        player2 = new Player("Lorenzo",WIZ2, WHITE);
        player3 = new Player("Giovanni",WIZ3, GRAY);
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


        Pawns exampleIsland = new Pawns();
        exampleIsland.addColor(GREEN,0);
        exampleIsland.addColor(BLUE,3);
        exampleIsland.addColor(YELLOW,1);
        game.getBoard().getIslands().get(4).addStudent(exampleIsland);


        Pawns exampleIsland2 = new Pawns();
        exampleIsland2.addColor(GREEN,1);
        exampleIsland2.addColor(BLUE,3);
        exampleIsland2.addColor(YELLOW,1);
        game.getBoard().getIslands().get(1).addStudent(exampleIsland2);
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
        assertEquals(Optional.of(WHITE), game.getBoard().moveMotherNature(2, game.getPlayers()));
    }

    @Test
    void conquerIslandWithTowerofTheWinner(){
        game.getBoard().setMotherNaturePos(2);
        game.getBoard().getIslands().get(4).addTower(WHITE);
        assertEquals(Optional.empty(), game.getBoard().moveMotherNature(2, game.getPlayers()));
    }

    @Test
    void conquerIslandWithTowerofTheLoser(){
        game.getBoard().setMotherNaturePos(2);
        game.getBoard().getIslands().get(4).addTower(GRAY);
        assertEquals(Optional.of(WHITE), game.getBoard().moveMotherNature(2, game.getPlayers()));
    }

    @Test
    void tieOnIslandWithTower(){
        game.getBoard().setMotherNaturePos(3);
        game.getBoard().getIslands().get(5).addTower(BLACK);
        assertEquals(Optional.empty(), game.getBoard().moveMotherNature(2, game.getPlayers()));
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
        game.getBoard().getIslands().get(1).addTower(BLACK);
        game.getBoard().getIslands().get(1).addStudent(BLUE);
        assertEquals(Optional.of(WHITE), game.getBoard().moveMotherNature(3, game.getPlayers()));
    }

    @Test
    void centaurStrategy(){
        centaurStrategy = new CentaurStrategy();
        game.getBoard().setStrategy(centaurStrategy);
        game.getBoard().setMotherNaturePos(10);
        game.getBoard().getIslands().get(1).addTower(BLACK);
        game.getBoard().getIslands().get(1).addStudent(BLUE);
        assertEquals(Optional.of(WHITE), game.getBoard().moveMotherNature(3, game.getPlayers()));
    }

    @Test
    void KnightStrategy(){
        knightStrategy = new KnightStrategy();
        game.getBoard().setStrategy(knightStrategy);
        knightStrategy.setTowerplus(BLACK);
        game.getBoard().setMotherNaturePos(10);
        game.getBoard().getIslands().get(1).addStudent(YELLOW);
        assertEquals(Optional.of(BLACK), game.getBoard().moveMotherNature(3, game.getPlayers()));
    }

    @Test
    void setInitialBoardTest() {
        initialSack = new Sack();
        initialSack.initialFill();
        initialBoard = new Board();
        initialBoard.initIslands(initialSack);
        assertEquals(initialBoard.getMotherNaturePos(),0);
        assertEquals(initialBoard.getSpecificIsland(6).getStudents().totalElements(),0);
        assertEquals(initialBoard.getSpecificIsland(0).getStudents().totalElements(),0);
        for (int i=1;i<MAX_ISLAND;i++) {
            if (i!=6) assertEquals(initialBoard.getSpecificIsland(i).getStudents().totalElements(),1);
        }
    }
}
