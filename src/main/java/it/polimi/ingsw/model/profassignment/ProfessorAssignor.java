package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * The ProfessorAssignor can assign a professor to a player using the strategy in use
 * It also contains the professor not assigned yet.
 */
public class ProfessorAssignor {
    private ProfessorStrategy professorStrategy;
    private final Pawns profsNotYetAssigned;

    public ProfessorAssignor() {
        this.professorStrategy = new StandardProfStrategy();
        profsNotYetAssigned = new Pawns();
        for(PawnColor pawnColor: PawnColor.values()){
            profsNotYetAssigned.addColor(pawnColor);
        }
    }

    /**
     * This method is called whenever a student is placed in a Hall
     * @param pawnColor the color to control
     * @param players the list of players in game
     * @return the player who win the professor
     */
    public Player colorProfessorChecker(PawnColor pawnColor, List<Player> players){
        List<Player> winners = professorStrategy.colorProfessorCheck(players, pawnColor);
        if(winners.size() == 1) {
            if (profsNotYetAssigned.getFromColor(pawnColor) == 1) {
                profsNotYetAssigned.removeColor(pawnColor);
                winners.get(0).getSchool().getProfessorTable().addColor(pawnColor);
                return winners.get(0);
            } else {
                for(Player player: players){
                    if(player.getSchool().getProfessorTable().getFromColor(pawnColor) == 1 && !player.equals(winners.get(0))){
                        player.getSchool().removeProfessor(pawnColor);
                        winners.get(0).getSchool().getProfessorTable().addColor(pawnColor);
                        return winners.get(0);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Reset Strategy to the standard one
     * This is done at the beginning of each {@link Player}'s turn
     */
    public void resetStrategy(){
        this.professorStrategy = new StandardProfStrategy();
    }

    /**
     * Change the strategy in use
     * @param professorStrategy the strategy to use for this turn
     */
    public void setProfessorStrategy(ProfessorStrategy professorStrategy) {
        this.professorStrategy = professorStrategy;
    }

    /**
     * @return the professors not assigned yet
     */
    public Pawns getProfsNotYetAssigned() {
        return profsNotYetAssigned;
    }

    /**
     * @return the current professor assignment strategy
     */
    public ProfessorStrategy getProfessorStrategy() {
        return professorStrategy;
    }
}
