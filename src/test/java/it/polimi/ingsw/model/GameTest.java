package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.model.influencecalculator.StandardStrategy;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.profassignment.StandardProfStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();

        game.addPlayer("Luca", Wizard.KING, TowerColor.BLACK);
        game.addPlayer("Marco", Wizard.SORCERER, TowerColor.WHITE);

        game.init();
    }

    /**
     * Test start game method: the character deck size, the island with a student on it, the number of student in the sack
     */
    @Test
    void startGameTest() {
        game.startGame(false);
        assertEquals(Constants.CHARACTER_IN_USE, game.getCharacterInUse().size());
        int expected = (Constants.STUDENTS_OF_EACH_COLOR - Constants.INIT_SACK_STUDENTS_PER_COLOR) * PawnColor.values().length
                - game.getPlayers().size() * game.getGameLimit().getMaxEntrance();
        assertEquals(expected, game.getSack().getNumberOfPawns());
        for (int i = 1; i < Constants.MAX_ISLAND; i++) {
            if (i != 6)
                assertEquals(1, game.getBoard().getIslands().get(i).getStudents().totalElements());
        }

        for (Player player : game.getPlayers()) {
            assertEquals(game.getGameLimit().getNumberOfTower(), player.getTowerNum());
        }
    }

    /**
     * Test getPlayerByName() method when that Player is present
     */
    @Test
    void getPlayerByName_ifPresent() {
        assertEquals("Luca", game.getPlayerByName("Luca").getPlayerName());
    }

    /**
     * Test getPlayerByName() method when that Player is not present
     */
    @Test
    void getPlayerByName_ifNotPresent() {
        assertNull(game.getPlayerByName("Tony"));
    }

    /**
     * Test getPlayerByTowerColor() method when that Player is present
     */
    @Test
    void getPlayerByTowerColor_ifPresent() {
        assertEquals("Luca", game.getPlayerByTowerColor(TowerColor.BLACK).getPlayerName());
    }

    /**
     * Test getPlayerByTowerColor() method when that Player is not present
     */
    @Test
    void getPlayerByTowerColor_ifNotPresent() {
        assertNull(game.getPlayerByTowerColor(TowerColor.GREY));
    }

    /**
     * Test nextPlayer method()
     */
    @Test
    void nextPlayerFirst() {
        game.setCurrentPlayer("Marco");
        assertEquals(game.getPlayerByName("Marco"), game.getCurrentPlayer());
        assertEquals("Luca", game.nextPlayer());
    }

    /**
     * Test nextPlayer method()
     */
    @Test
    void nextPlayerLast() {
        game.setCurrentPlayer("Luca");
        assertEquals(game.getPlayerByName("Luca"), game.getCurrentPlayer());
        assertEquals("Marco", game.nextPlayer());
    }

    /**
     * Test Move Mother Nature movement. In the example, elephant has movement = 1 so this is the limit
     */
    @Test
    void moveMotherNature() {
        game.getPlayers().get(0).playAssistant(Assistant.ELEPHANT);
        assertTrue(game.moveMotherNature(1, game.getPlayers().get(0)));
        assertFalse(game.moveMotherNature(0, game.getPlayers().get(0)));
        assertFalse(game.moveMotherNature(2, game.getPlayers().get(0)));
    }

    @Test
    void fillCloudTest() {
        game.startGame(false);
        List<Cloud> cloudList = game.getClouds();
        for (Cloud cloud : cloudList) {
            assertEquals(new Pawns(), cloud.getStudents());
        }
        game.fillClouds();
        for (Cloud cloud : cloudList) {
            assertNotEquals(new Pawns(), cloud.getStudents());
            assertEquals(game.getGameLimit().getStudentOnCloud(), cloud.getStudents().totalElements());
        }
    }

    @Test
    void pickFromCloudTest() {
        Cloud cloud = game.getClouds().get(0);
        Pawns pawnsOnCloud = cloud.getStudents();
        Player player = game.getPlayerByName("Luca");

        //CLEAR ENTRANCE
        for (PawnColor pawnColor : PawnColor.values())
            player.getSchool().getEntrance().removeColor(pawnColor, 7);

        Pawns alreadyInEntrance = new Pawns(3, 0, 0, 0, 0);
        player.getSchool().getEntrance().addPawns(alreadyInEntrance);
        game.setCurrentPlayer(player.getPlayerName());
        game.pickFromCloud(0);

        Pawns EntranceAfterPicking = new Pawns();
        EntranceAfterPicking.addPawns(alreadyInEntrance);
        EntranceAfterPicking.addPawns(pawnsOnCloud);

        assertEquals(EntranceAfterPicking, player.getSchool().getEntrance());

    }

    @Test
    void getPlayableAssistantTest() {
        game.endTurn();
        game.setCurrentPlayer("Marco");
        game.playAssistant(Assistant.EAGLE);
        assertFalse(game.getPlayableAssistant().contains(Assistant.EAGLE));
        for (Assistant assistant : Assistant.values()) {
            if (!assistant.equals(Assistant.EAGLE))
                assertTrue(game.getPlayableAssistant().contains(assistant));
        }
    }

    /**
     * Tests the rare case when a player has only played assistant in his hand, in this case it's ok to play them.
     */
    @Test
    void getPlayableAssistantTest_1() {
        game.endTurn();
        for (Assistant assistant : Assistant.values()) {
            if (!assistant.equals(Assistant.TURTLE))
                game.getPlayerByName("Marco").playAssistant(assistant);
        } //now Marco has only turtle in his hand
        game.setCurrentPlayer("Luca");
        game.playAssistant(Assistant.TURTLE); //Luca plays turtle, the only card that Marco has.
        game.setCurrentPlayer("Marco");
        assertTrue(game.getPlayableAssistant().contains(Assistant.TURTLE)); //playable-by-Marco assistants must contain Turtle
    }

    /**
     * Test the reset strategy method
     */
    @Test
    void resetStrategiesTest() {
        game.resetStrategies();
        assertTrue(game.getProfessorAssignor().getProfessorStrategy() instanceof StandardProfStrategy);
        assertTrue(game.getBoard().getInfluenceStrategy() instanceof StandardStrategy);
    }
}