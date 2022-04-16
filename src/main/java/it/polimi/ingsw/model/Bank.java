package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the Bank used in expert mode
 */
public class Bank {
    private final Map<Player,Integer> cashMap;
    private int generalBank;

    public Bank() {
        cashMap = new HashMap<>();
        generalBank = Constants.MAX_NUM_OF_COINS;
    }

    /**
     * @return the cash in the bank
     */
    public int getGeneralBank() {
        return generalBank;
    }

    /**
     * Getter of each player money
     * @param player cash holder
     * @return money of the player
     */
    public int getCashByPlayer(Player player){
        return cashMap.get(player);
    }

    /**
     * Initialize a player. Gives him a coin taken from the bank
     * @param player to initialize
     */
    public void initPlayer(Player player){
        if(generalBank > 0) {
            generalBank -= 1;
            cashMap.put(player, 1);
        }
    }

    /**
     * Add a coin to the player
     * @param player to reward
     * @return true if player was rewarded, otherwise false
     */
    public boolean reward(Player player){
        if(generalBank > 0){
            generalBank -= 1;
            cashMap.put(player,cashMap.get(player) + 1);
            return true;
        }
        return false;
    }

    /**
     * Take coins from a player
     * @param player to take money from
     * @param coin number of coins to take
     * @return true if player can pay that amount of coins (and pays it), otherwise false
     */
    public boolean pay(Player player, int coin){
        if(cashMap.get(player) >= coin){
            cashMap.put(player,cashMap.get(player) - coin);
            generalBank += coin;
            return true;
        }
        return false;
    }
}
