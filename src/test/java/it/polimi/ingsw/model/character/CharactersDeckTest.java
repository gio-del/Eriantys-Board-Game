package it.polimi.ingsw.model.character;

import it.polimi.ingsw.constants.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharactersDeckTest {
    private CharactersDeck charactersDeck;

    @BeforeEach
    void setUp() {
        charactersDeck = new CharactersDeck();
        charactersDeck.init();
    }

    @Test
    void extractCharacterInUseTest() {
        assertEquals(Constants.NUM_OF_CHARACTER_IN_USE,charactersDeck.extractCharacterInUse().size());
    }
}
