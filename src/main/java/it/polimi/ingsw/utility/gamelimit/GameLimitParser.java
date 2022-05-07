package it.polimi.ingsw.utility.gamelimit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.network.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;


public class GameLimitParser {

    /**
     * @return a list with the {@link GameLimitData} if it parsed correctly, an empty map otherwise
     */
    public Map<Integer,GameLimitData> parseLimit(){
        Gson gson = new Gson();
        Type type = new TypeToken<Map<Integer,GameLimitData>>(){}.getType();
        Map<Integer,GameLimitData> gameLimitDataMap;
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(Constants.GAME_LIMIT_MAP_PATH))));
            gameLimitDataMap = gson.fromJson(buffer, type);
            return gameLimitDataMap;
        } catch(NullPointerException e){
            Server.LOGGER.severe(() -> "Server couldn't start. Failed to read GameLimit json file.");
            System.exit(1);
        }
        return Map.of();
    }
}
