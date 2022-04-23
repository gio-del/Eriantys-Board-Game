package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.GameLimit;
import it.polimi.ingsw.model.place.HallManager;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SetProfStratActionTest {
    Player player;
    ProfessorAssignor professorAssignor;
    @BeforeEach
    void setUp() {
        player = new Player("Bob", Wizard.KING, TowerColor.BLACK,new GameLimit(false),new HallManager());
        professorAssignor = new ProfessorAssignor();
    }

    @Test
    void settingFarmerStrategyTest() {
        Action setProfStratAction = new SetProfStratAction("Farmer",professorAssignor,player);
        assertEquals("StandardProfStrategy",professorAssignor.getProfessorStrategy().getClass().getSimpleName());
        setProfStratAction.apply();
        assertEquals("FarmerProfStrategy",professorAssignor.getProfessorStrategy().getClass().getSimpleName());
    }
}
