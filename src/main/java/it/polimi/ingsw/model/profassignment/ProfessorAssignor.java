package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

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

    public void resetStrategy(){
        this.professorStrategy = new StandardProfStrategy();
    }

    public void setProfessorStrategy(ProfessorStrategy professorStrategy) {
        this.professorStrategy = professorStrategy;
    }

    public Pawns getProfsNotYetAssigned() {
        return profsNotYetAssigned;
    }
}
