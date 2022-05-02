package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.Bank;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;

import java.util.*;

/**
 * This Class manages the hall of all players in game. In addition to checking professor color, this checks the assignment of the coin to the players.
 * It uses a {@link Bank} to manage coins
 */
public class HallManager {
    private static final List<Integer> rewardPositions = Arrays.asList(3,6,9);
    private final Map<Place,Player> hallMap;
    private final Bank bank;
    private final List<Player> playerList;
    private final ProfessorAssignor professorAssignor;

    public HallManager(Bank bank) {
        this.playerList = new ArrayList<>();
        this.professorAssignor = new ProfessorAssignor();
        this.bank = bank;
        hallMap = new HashMap<>();
    }

    /**
     * Add player a player to the list
     * @param player to be added
     */
    public void addPlayer(Player player) {
        this.playerList.add(player);
        hallMap.put(player.getSchool().getHallAsPlace(),player);
        bank.initPlayer(player);
    }

    /**
     * @param observable hall that triggered this method
     * @param pawnColor the pawns on which to check professor assignment and to check coin assignment
     */
    public void update(Hall observable, PawnColor pawnColor) {
        professorAssignor.colorProfessorChecker(pawnColor,playerList);
        if(rewardPositions.contains(observable.getPawns().getFromColor(pawnColor))){
            reward(hallMap.get(observable));
        }
    }

    /**
     * Reset strategy to the standard one
     */
    public void resetStrategy(){
        professorAssignor.resetStrategy();
    }

    /**
     * Add a coin to a player
     * @param player to reward
     */
    private void reward(Player player){
        bank.reward(player);
    }

    public ProfessorAssignor getProfessorAssignor() {
        return professorAssignor;
    }
}
