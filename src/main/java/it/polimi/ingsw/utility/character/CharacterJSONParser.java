package it.polimi.ingsw.utility.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.actiondata.SetInfluenceStrategyData;
import it.polimi.ingsw.network.server.Server;

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
    public List<CharacterCard> parseCharacters() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(SetInfluenceStrategyData.class);
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(Constants.CHARACTER_JSON_PATH))));
            return Arrays.stream(mapper.readValue(buffer, CharacterCard[].class)).toList();
        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
            Server.LOGGER.severe(() -> "Server couldn't start. Failed to read character json file.");
            System.exit(1);
        }
        return List.of();
    }
}
