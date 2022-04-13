package it.polimi.ingsw.model.place;


import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Observer of the {@link Hall} of each Player.
 * When a Player is created, it initialises its {@link School} and subscribe to the {@link HallObserver}
 */
public class HallObserver {
    //TODO: add a mechanism to assign coin to player, but only in "expert mode" game
    private final List<Player> playerList;
    private final ProfessorAssignor professorAssignor;

    public HallObserver() {
        this.playerList = new ArrayList<>();
        this.professorAssignor = new ProfessorAssignor();
    }

    /**
     * Add a {@link Player} to the subscribers list
     * @param player to be added
     */
    public void addPlayer(Player player){
        this.playerList.add(player);
    }

    /**
     * This method is called everytime a pawn is added or moved to the {@link Hall}
     * @param pawnColor the pawns on which to check professor assignment
     */
    public void update(PawnColor pawnColor){
        professorAssignor.colorProfessorChecker(pawnColor,playerList);
    }

    /**
     * Reset strategy to the standard one
     */
    public void resetStrategy(){
        professorAssignor.resetStrategy();
    }

    public ProfessorAssignor getProfessorAssignor() {
        return professorAssignor;
    }
}
