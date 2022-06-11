package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Bank;
import it.polimi.ingsw.model.place.HallManager;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;
import it.polimi.ingsw.utility.gamelimit.GameLimit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SetProfStrategyActionTest {
    Player player;
    ProfessorAssignor professorAssignor;

    @BeforeEach
    void setUp() {
        player = new Player("Bob", Wizard.KING, TowerColor.BLACK, GameLimit.getLimit(2), new HallManager(new Bank()));
        professorAssignor = new ProfessorAssignor();
    }

    /**
     * Test the method that set a new Strategy to the assignment of professors.
     */
    @Test
    void settingFarmerStrategyTest() {
        Action setProfStrategyAction = new SetProfStrategyAction("Farmer", professorAssignor, player);
        assertEquals("StandardProfStrategy", professorAssignor.getProfessorStrategy().getClass().getSimpleName());
        assertTrue(setProfStrategyAction.apply());
        assertEquals("FarmerProfStrategy", professorAssignor.getProfessorStrategy().getClass().getSimpleName());
    }

    @Test
    void settingNotExistingStrategyTest() {
        Action setProfStrategyAction = new SetProfStrategyAction("null", professorAssignor, player);
        assertEquals("StandardProfStrategy", professorAssignor.getProfessorStrategy().getClass().getSimpleName());
        assertFalse(setProfStrategyAction.apply());
        assertEquals("StandardProfStrategy", professorAssignor.getProfessorStrategy().getClass().getSimpleName());
    }
}
