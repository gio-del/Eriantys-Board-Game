package it.polimi.ingsw.model.character;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.utils.CharacterJSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterJSONParserTest {

    List<CharacterCard> characterCardList;

    @BeforeEach
    void setUp() {
        characterCardList = new CharacterJSONParser().parseCharacters(Constants.CHARACTER_JSON_PATH);
    }

    @Test
    void testJSONParser() {
        String[] strings = {"4","1"};
        List<String> characterPar = Arrays.asList(strings);
        CharacterCard characterCard = new CharacterCard("Monk",1,"Take a student from the card and place him on an island of your choice. Then, take a student out of the sack and place it on this card",ActionType.MOVE_ACTION,characterPar);
        assertEquals(characterCard,characterCardList.get(0));
    }

    @Test
    void parseCharacters() {
        assertTrue(characterCardList.size()>0);
    }

}
