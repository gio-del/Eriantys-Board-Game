package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.Bank;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

/**
 * This Class is an extension of the {@link HallManager}. In addition to checking professor color, this check the assignment of the coin to the players.
 * It uses a {@link Bank} to manage coins
 */
public class BankHallManager extends HallManager {
    private static final List<Integer> rewardPositions = Arrays.asList(3,6,9);
    private final Map<Place,Player> hallMap;
    private final Bank bank;

    public BankHallManager(Bank bank) {
        super();
        this.bank = bank;
        hallMap = new HashMap<>();
    }

    /**
     * Add player a player to the list
     * @param player to be added
     */
    @Override
    public void addPlayer(Player player) {
        super.addPlayer(player);
        hallMap.put(player.getSchool().getHallAsPlace(),player);
        bank.initPlayer(player);
    }

    /**
     * @param observable hall that triggered this method
     * @param pawnColor the pawns on which to check professor assignment and to check coin assignment
     */
    @Override
    public void update(Hall observable, PawnColor pawnColor) {
        super.update(observable, pawnColor);
        if(rewardPositions.contains(observable.getPawns().getFromColor(pawnColor))){
            reward(hallMap.get(observable));
        }
    }

    /**
     * Add a coin to a player
     * @param player to reward
     */
    private void reward(Player player){
        bank.reward(player);
    }
}
