package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Bank;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.place.HallManager;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.utility.gamelimit.GameLimit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SetInfluenceStrategyActionTest {
    private Board board;
    private Player player;

    @BeforeEach
    void setUp() {
        board = new Board();
        player = new Player("Alice", Wizard.KING, TowerColor.BLACK, GameLimit.getLimit(2), new HallManager(new Bank()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Centaur", "Knight", "Mushroom Seller"})
    void SetStrategyTest(String arg) {
        assertEquals("StandardStrategy",board.getInfluenceStrategy().getClass().getSimpleName());
        CharacterCard stub = new CharacterCard();
        stub.setName(arg);
        assertTrue(new SetInfluenceStrategyAction(stub,board,player).apply());
        assertEquals((arg+"Strategy").replace(" ",""),board.getInfluenceStrategy().getClass().getSimpleName());
    }

    @Test
    void SetDefaultStrategyTest() {
        assertEquals("StandardStrategy",board.getInfluenceStrategy().getClass().getSimpleName());
        CharacterCard stub = new CharacterCard();
        stub.setName("null");
        assertFalse(new SetInfluenceStrategyAction(stub,board,player).apply());
        assertEquals("StandardStrategy",board.getInfluenceStrategy().getClass().getSimpleName());

    }
}
