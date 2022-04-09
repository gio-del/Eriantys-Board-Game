package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.TowerColor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the limit of a specific Game.
 * Constants and common to all possible games limit are stored in {@link Constants}
 */
public class GameLimit {
    private final int maxEntrance;
    private final int studentOnCloud;
    private final int numberOfTower;
    private final List<TowerColor> towerColors;

    /**
     * The constructor build a GameLimit based on the number of player reading on a specific JSON file
     */
    public GameLimit(boolean isThreePlayer) {
        Gson gson = new Gson();
        GameLimit gameLimit;
        BufferedReader buffer;
        if(isThreePlayer){
            buffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(GameLimit.class.getResourceAsStream(Constants.THREE_PLAYER_LIMIT_JSON_PATH))));
        }
        else{
            buffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(GameLimit.class.getResourceAsStream(Constants.TWO_FOUR_PLAYER_LIMIT_JSON_PATH))));
        }
        gameLimit = gson.fromJson(buffer,GameLimit.class);
        this.maxEntrance = gameLimit.maxEntrance;
        this.numberOfTower = gameLimit.numberOfTower;
        this.towerColors = gameLimit.towerColors;
        this.studentOnCloud = gameLimit.studentOnCloud;
    }

    public int getMaxEntrance() {
        return maxEntrance;
    }

    public int getStudentOnCloud() {
        return studentOnCloud;
    }

    public int getNumberOfTower() {
        return numberOfTower;
    }

    public List<TowerColor> getTowerColors() {
        return towerColors;
    }
}
