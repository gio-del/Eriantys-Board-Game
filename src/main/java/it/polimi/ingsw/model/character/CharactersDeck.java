package it.polimi.ingsw.model.character;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.utils.CharacterJSONParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CharactersDeck {

    private List<CharacterCard> characterCards;

    public CharactersDeck() {
        this.characterCards = new ArrayList<>();
    }

    public void init(){
            this.characterCards = CharacterJSONParser.parseCharacters(Constants.CharacterJSONPath);
    }

    public List<CharacterCard> extractCharacterInUse(){
        Collections.shuffle(characterCards);
        return characterCards.stream().limit(Constants.NumOfCharacterInUse).toList();
    }
}
