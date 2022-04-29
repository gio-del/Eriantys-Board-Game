package it.polimi.ingsw.model.character;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.utils.CharacterJSONParser;

import java.util.Collections;
import java.util.List;


public class CharactersDeck {
// TODO: move this to server "high-level" all character cards are common to all games. If characterCards is empty after parsing, the server cannot start.
    private static final List<CharacterCard> characterCards = new CharacterJSONParser().parseCharacters(Constants.CHARACTER_JSON_PATH);

    public List<CharacterCard> extractCharacterInUse(){
        Collections.shuffle(characterCards);
        return characterCards.stream().limit(Constants.CHARACTER_IN_USE).toList();
    }
}
