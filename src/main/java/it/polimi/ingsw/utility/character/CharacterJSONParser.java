package it.polimi.ingsw.utility.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.character.CharacterCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CharacterJSONParser {
    /**
     * @return a list with all the {@link CharacterCard} if it parsed correctly, an empty list otherwise
     */
    public List<CharacterCard> parseCharacters(String pathToJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(pathToJSON))));
            return Arrays.stream(mapper.readValue(buffer, CharacterCard[].class)).toList();
        } catch (NullPointerException | IOException e) {
            return List.of();
        }
    }
}
