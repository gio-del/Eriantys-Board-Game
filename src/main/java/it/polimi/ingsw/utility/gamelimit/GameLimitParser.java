package it.polimi.ingsw.utility.gamelimit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.constants.Constants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

public class GameLimitParser {

    /**
     * @return a list with the {@link GameLimitData} if it parsed correctly, an empty map otherwise
     */
    public Map<Integer,GameLimitData> parseLimit(){
        Gson gson = new Gson();
        Type type = new TypeToken<Map<Integer,GameLimitData>>(){}.getType();
        Map<Integer,GameLimitData> gameLimitDataMap;
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(Constants.GAME_LIMIT_MAP_PATH));
            gameLimitDataMap = gson.fromJson(buffer,type);
        } catch (FileNotFoundException e) {
            return Map.of();
        }
        return gameLimitDataMap;
    }
}
