package it.polimi.ingsw.utility.character;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.character.CharacterCard;

import java.util.Collections;
import java.util.List;


public class CharactersDeck {
   private static final List<CharacterCard> characterCards = new CharacterJSONParser().parseCharacters();

   private CharactersDeck(){}

    public static List<CharacterCard> extractCharacterInUse(){
        Collections.shuffle(characterCards);
        return characterCards.stream().limit(Constants.CHARACTER_IN_USE).toList();
    }

    public static boolean ok(){
        return !characterCards.equals(List.of());
    }
}
