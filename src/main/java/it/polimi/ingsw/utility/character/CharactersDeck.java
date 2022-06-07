package it.polimi.ingsw.utility.character;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.network.server.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CharactersDeck {
    private static final List<CharacterCard> characterCards = new CharacterJSONParser().parseCharacters(Constants.CHARACTER_JSON_PATH);

    private CharactersDeck() {
        //cannot be instantiated
    }

    /**
     * This function is called by the games, and return a random list of 3 character that can be used during a match
     * @return a list of 3 character
     */
    public static List<CharacterCard> extractCharacterInUse() {
        List<CharacterCard> characters = new ArrayList<>();
        characterCards.forEach(character -> characters.add(new CharacterCard(character)));
        Collections.shuffle(characters);
        return characters.stream().limit(Constants.CHARACTER_IN_USE).toList();
    }

    /**
     * Called during server booting phase, if the character list is empty (meaning that there was an error or the JSON file is missing) the server cannot start.
     */
    public static void start() {
        if(characterCards.isEmpty()) {
            Server.LOGGER.severe(() -> "Server couldn't start. Failed to read character json file.");
            System.exit(1);
        }
    }
}
