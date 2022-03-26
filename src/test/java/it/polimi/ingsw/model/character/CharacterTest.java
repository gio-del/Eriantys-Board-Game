package it.polimi.ingsw.model.character;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.utils.CharacterJSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class CharacterTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void parseCharacters() {
        List<CharacterCard> characterCardList = CharacterJSONParser.parseCharacters(Constants.CharacterJSONPath);
        assertTrue(characterCardList.size()>0);
    }
}
