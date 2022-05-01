package it.polimi.ingsw.utility;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.utility.character.CharactersDeck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharactersDeckTest {
    @Test
    void extractCharacterInUseTest() {
        assertEquals(Constants.CHARACTER_IN_USE, CharactersDeck.extractCharacterInUse().size());
    }
}
