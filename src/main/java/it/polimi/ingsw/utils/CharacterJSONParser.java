package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.character.CharacterCard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CharacterJSONParser {

    /**
     * @param filePath the filePath to the JSON file containing characters info
     * @return a list with all the {@link CharacterCard} if it parsed correctly, an empty list otherwise
     */
    public List<CharacterCard> parseCharacters(String filePath) {
        Gson gson = new Gson();
        Type charList = new TypeToken<ArrayList<CharacterCard>>(){}.getType();
        List<CharacterCard> characterCardList;
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            characterCardList = gson.fromJson(buffer,charList);
        } catch (FileNotFoundException e) {
            return List.of();
        }
        return characterCardList;
    }
}
