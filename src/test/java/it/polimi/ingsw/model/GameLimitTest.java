package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameLimitTest {
    GameLimit gameLimit;

    @BeforeEach
    void setUp() {
    }

    /**
     * Check that the three player limit JSON file is read correctly
     */
    @Test
    void checkThreePlayerLimit() {
        gameLimit = new GameLimit(true);
        assertEquals(9,gameLimit.getMaxEntrance());
        assertEquals(6,gameLimit.getNumberOfTower());
        assertEquals(4,gameLimit.getStudentOnCloud());
        List<TowerColor> towerColors = new ArrayList<>(Arrays.stream(TowerColor.values()).toList());
        assertEquals(towerColors, gameLimit.getTowerColors());
    }

    /**
     * Check that the two or four player limit JSON file is read correctly
     */
    @Test
    void checkTwoFourPlayerLimit() {
        gameLimit = new GameLimit(false);
        assertEquals(7,gameLimit.getMaxEntrance());
        assertEquals(8,gameLimit.getNumberOfTower());
        assertEquals(3,gameLimit.getStudentOnCloud());
        List<TowerColor> towerColors = new ArrayList<>(Arrays.stream(TowerColor.values()).toList());
        towerColors.remove(TowerColor.GRAY);
        assertEquals(towerColors, gameLimit.getTowerColors());
    }
}
