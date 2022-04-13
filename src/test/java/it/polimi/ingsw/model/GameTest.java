package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.influencecalculator.StandardStrategy;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.profassignment.StandardProfStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(2);

        game.addPlayer("Luca", Wizard.WIZ1, TowerColor.BLACK);
        game.addPlayer("Marco", Wizard.WIZ2,TowerColor.GRAY);

    }

    /**
     * Test start game method: the character deck size, the island with a student on it, the number of student in the sack
     */
    @Test
    void startGameTest() {
        game.startGame();
        assertEquals(Constants.CHARACTER_IN_USE, game.getCharacterInUse().size());
        int expected = (Constants.STUDENTS_OF_EACH_COLOR-Constants.INIT_SACK_STUDENTS_PER_COLOR)*PawnColor.values().length
                - game.getNPlayers()*game.getGameLimit().getMaxEntrance();
        assertEquals(expected,game.getSack().getNumberOfPawns());
        for(int i=1;i<Constants.MAX_ISLAND;i++){
            if(i!=6)
                assertEquals(1,game.getBoard().getIslands().get(i).getStudents().totalElements());
        }
    }

    /**
     * Test getPlayerByName() method when that Player is present
     */
    @Test
    void getPlayerByName_ifPresent() {
        assertEquals("Luca",game.getPlayerByName("Luca").getPlayerName());
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
        assertEquals("Luca",game.getPlayerByTowerColor(TowerColor.BLACK).getPlayerName());
    }

    /**
     * Test getPlayerByTowerColor() method when that Player is not present
     */
    @Test
    void getPlayerByTowerColor_ifNotPresent() {
        assertNull(game.getPlayerByTowerColor(TowerColor.WHITE));
    }

    /**
     * Test remove player method
     */
    @Test
    void removePlayerTest() {
        Player player = game.getPlayerByName("Luca");
        assertTrue(game.removePlayer(player));
        assertFalse(game.getPlayers().contains(player));
    }

    /**
     * Check what happens if a player with same wizard is added.
     */
    @Test
    void addPlayerWithSameWizardTest() {
        assertFalse(game.addPlayer("Matteo",Wizard.WIZ1,TowerColor.GRAY));
    }

    /**
     * Check what happens if the game is full and a player is added
     */
    @Test
    void addPlayerIfGameIsFull(){
        assertFalse(game.addPlayer("Matteo",Wizard.WIZ3,TowerColor.BLACK));
    }

    /**
     * Test nextPlayer method()
     */
    @Test
    void nextPlayerFirst(){
        game.setCurrentPlayer(game.getPlayers().get(1));
        assertEquals("Luca", game.nextPlayer().getPlayerName());
    }

    /**
     * Test nextPlayer method()
     */
    @Test
    void nextPlayerLast(){
        game.setCurrentPlayer(game.getPlayers().get(0));
        assertEquals("Marco", game.nextPlayer().getPlayerName());
    }

    /**
     * Test Move Mother Nature test. In the example, elephant has movement = 1 so this is the limit
     */
    @Test
    void moveMotherNature(){
        game.getPlayers().get(0).playAssistant(Assistant.ELEPHANT);
        assertTrue(game.moveMotherNature(1, game.getPlayers().get(0)));
        assertFalse(game.moveMotherNature(0, game.getPlayers().get(0)));
        assertFalse(game.moveMotherNature(2, game.getPlayers().get(0)));
    }

    /**
     * Test the reset strategy method
     */
    @Test
    void resetStrategiesTest() {
        assertTrue(game.resetStrategies());
        assertTrue(game.getProfessorAssignor().getProfessorStrategy() instanceof StandardProfStrategy);
        assertTrue(game.getBoard().getInfluenceStrategy() instanceof StandardStrategy);
    }
}