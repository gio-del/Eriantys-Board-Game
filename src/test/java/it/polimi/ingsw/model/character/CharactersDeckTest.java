package it.polimi.ingsw.model.character;

import it.polimi.ingsw.constants.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharactersDeckTest {
    private CharactersDeck charactersDeck;

    @BeforeEach
    void setUp() {
        charactersDeck = new CharactersDeck();
    }

    @Test
    void extractCharacterInUseTest() {
        assertEquals(Constants.CHARACTER_IN_USE,charactersDeck.extractCharacterInUse().size());
    }
}
