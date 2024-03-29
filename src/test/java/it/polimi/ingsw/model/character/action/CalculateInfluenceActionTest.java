package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.player.TowerColor.BLACK;
import static it.polimi.ingsw.model.player.TowerColor.WHITE;
import static org.junit.jupiter.api.Assertions.*;

class CalculateInfluenceActionTest {
    private List<Player> players = new ArrayList<>();
    private Board board;
    private Island island;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        game.addPlayer("Luca", Wizard.KING, BLACK);
        game.addPlayer("Paolo", Wizard.SORCERER, WHITE);
        game.init();

        board = game.getBoard();
        island = board.getIslands().get(0);

        island.add(new Pawns(3, 1, 0, 2, 0));

        game.getPlayerByName("Luca").getSchool().getProfessorTable().addPawns(new Pawns(PawnColor.GREEN));

        game.getPlayerByName("Paolo").getSchool().getProfessorTable().addPawns(new Pawns(PawnColor.RED));

        players = game.getPlayers();
        island.addTower(WHITE);
    }

    /**
     * Test the card that calculate influence on a given island
     */
    @Test
    void calculateInfluenceTest() {
        assertTrue(new CalculateInfluenceAction(players, board, island).apply());
        assertNotNull(island.getTower());
        assertEquals(BLACK, island.getTower());
    }
}
