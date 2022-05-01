package it.polimi.ingsw.utility;

import it.polimi.ingsw.GameLimit;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.utility.gamelimit.GameLimitData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameLimitDataTest {
    GameLimitData gameLimitData;

    @BeforeEach
    void setUp() {
    }

    /**
     * Check that the three player limit JSON file is read correctly
     */
    @Test
    void checkThreePlayerLimit() {
        gameLimitData = GameLimit.getLimit(3);
        assertEquals(9, gameLimitData.getMaxEntrance());
        assertEquals(6, gameLimitData.getNumberOfTower());
        assertEquals(4, gameLimitData.getStudentOnCloud());
        Set<TowerColor> towerColors = EnumSet.allOf(TowerColor.class);
        assertEquals(towerColors, gameLimitData.getTowerColors());
    }

    /**
     * Check that the two or four player limit JSON file is read correctly
     */
    @Test
    void checkTwoFourPlayerLimit() {
        gameLimitData = GameLimit.getLimit(2);
        assertEquals(7, gameLimitData.getMaxEntrance());
        assertEquals(8, gameLimitData.getNumberOfTower());
        assertEquals(3, gameLimitData.getStudentOnCloud());
        Set<TowerColor> towerColors = EnumSet.allOf(TowerColor.class);
        towerColors.remove(TowerColor.GRAY);
        assertEquals(towerColors, gameLimitData.getTowerColors());
    }
}
