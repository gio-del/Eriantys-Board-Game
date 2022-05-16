package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.utility.character.CharacterJSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CharacterJSONParserTest {
    List<CharacterCard> characterCardList;

    @BeforeEach
    void setUp() {
        characterCardList = new CharacterJSONParser().parseCharacters();
    }

    @Test
    void parseCharacters() {
        assertTrue(characterCardList.size() > 0);
    }

}
