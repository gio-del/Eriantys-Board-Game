package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.action.ActionType;
import it.polimi.ingsw.utility.character.CharacterJSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharacterJSONParserTest {

    List<CharacterCard> characterCardList;

    @BeforeEach
    void setUp() {
        characterCardList = new CharacterJSONParser().parseCharacters();
    }

    @Test
    void testJSONParser() {
        String[] strings = {"4","1"};
        List<String> characterPar = Arrays.asList(strings);
        CharacterCard characterCard = new CharacterCard("Monk",1,"Take a student from the card and place him on an island of your choice. Then, take a student out of the sack and place it on this card", ActionType.MOVE,characterPar);
        assertEquals(characterCard,characterCardList.get(0));
    }

    @Test
    void parseCharacters() {
        assertTrue(characterCardList.size()>0);
    }

}
