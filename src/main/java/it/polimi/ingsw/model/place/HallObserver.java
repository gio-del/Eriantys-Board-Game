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
    //TODO: Remove singleton from this, add a mechanism to assign coin to player
    private static HallObserver instance;
    private static final List<Player> playerList = new ArrayList<>();
    private final ProfessorAssignor professorAssignor;

    private HallObserver() {
        professorAssignor = new ProfessorAssignor();
    }

    public static HallObserver getInstance(){
        if(instance == null){
            instance = new HallObserver();
        }
        return instance;
    }

    public static void resetInstance(){
        playerList.clear();
        instance = null;
    }

    /**
     * Add a {@link Player} to the subscribers list
     * @param player to be added
     */
    public static void addPlayer(Player player){
        playerList.add(player);
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
