package it.polimi.ingsw.utility.character;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.network.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharacterJSONParser {

    /**
     * @return a list with all the {@link CharacterCard} if it parsed correctly, an empty list otherwise
     */
    public List<CharacterCard> parseCharacters() {
        Gson gson = new Gson();
        Type charList = new TypeToken<ArrayList<CharacterCard>>(){}.getType();
        List<CharacterCard> characterCardList;
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(Constants.CHARACTER_JSON_PATH))));
            characterCardList = gson.fromJson(buffer, charList);
            return characterCardList;
        } catch (NullPointerException e){
            Server.LOGGER.severe(() -> "Server couldn't start. Failed to read character json file.");
            System.exit(1);
        }
        return List.of();
    }
}
