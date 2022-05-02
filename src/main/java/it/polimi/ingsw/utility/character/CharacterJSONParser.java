package it.polimi.ingsw.utility.character;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.character.CharacterCard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CharacterJSONParser {

    /**
     * @return a list with all the {@link CharacterCard} if it parsed correctly, an empty list otherwise
     */
    public List<CharacterCard> parseCharacters() {
        Gson gson = new Gson();
        Type charList = new TypeToken<ArrayList<CharacterCard>>(){}.getType();
        List<CharacterCard> characterCardList;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(Constants.CHARACTER_JSON_PATH)));
        characterCardList = gson.fromJson(buffer,charList);
        return characterCardList;
    }
}
