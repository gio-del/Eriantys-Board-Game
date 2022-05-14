package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.profassignment.FarmerProfStrategy;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;

public class SetProfStrategyAction implements Action {
    private final String influenceChoice;
    private final ProfessorAssignor professorAssignor;
    private final Player player;

    public SetProfStrategyAction(String influenceChoice, ProfessorAssignor professorAssignor, Player player) {
        this.influenceChoice = influenceChoice;
        this.professorAssignor = professorAssignor;
        this.player = player;
    }

    @Override
    public boolean apply() {
        if ("Farmer".equals(influenceChoice)) {
            professorAssignor.setProfessorStrategy(new FarmerProfStrategy(player));
            return true;
        } else return false;
    }
}
