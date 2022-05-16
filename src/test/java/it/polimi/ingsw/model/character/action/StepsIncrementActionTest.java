package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StepsIncrementActionTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void stepsIncrementActionTest() {
        assertTrue(new StepsIncrementAction(game, 2).apply());
    }
}
