package it.polimi.ingsw.utility.character;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.character.CharacterCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CharactersDeck {
   private static final List<CharacterCard> characterCards = new CharacterJSONParser().parseCharacters();

   private CharactersDeck(){}

    public static List<CharacterCard> extractCharacterInUse(){
       List<CharacterCard> clone = new ArrayList<>(characterCards);
       Collections.shuffle(clone);
       return clone.stream().limit(Constants.CHARACTER_IN_USE).toList();
    }

    public static void start() {
       //do nothing, if is not ok the parser will raise an exception and the server will stop
    }
}
