package it.polimi.ingsw.utility.gamelimit;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.TowerColor;

import java.util.Set;

/**
 * This class represents the limit of a specific Game.
 * Constants and common to all possible games limit are stored in {@link Constants}
 */
public class GameLimitData {
    private int maxEntrance;
    private int studentOnCloud;
    private int numberOfTower;
    private Set<TowerColor> towerColors;

    public int getMaxEntrance() {
        return maxEntrance;
    }

    public int getStudentOnCloud() {
        return studentOnCloud;
    }

    public int getNumberOfTower() {
        return numberOfTower;
    }

    public Set<TowerColor> getTowerColors() {
        return towerColors;
    }
}
