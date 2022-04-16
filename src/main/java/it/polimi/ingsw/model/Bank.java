package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.Player;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<Player,Integer> cashMap;
    private int generalBank;

    public Bank() {
        cashMap = new HashMap<>();
        generalBank = Constants.MAX_NUM_OF_COINS;
    }

    public int getGeneralBank() {
        return generalBank;
    }

    public int getCashByPlayer(Player player){
        return cashMap.get(player);
    }

    public void initPlayer(Player player){
        if(generalBank > 0) {
            generalBank -= 1;
            cashMap.put(player, 1);
        }
    }

    public boolean reward(Player player){
        return reward(player,1);
    }

    public boolean reward(Player player, int coin){
        if(generalBank >= coin){
            generalBank -= coin;
            cashMap.put(player,cashMap.get(player) + coin);
            return true;
        }
        return false;
    }

    public boolean pay(Player player, int coin){
        if(cashMap.get(player) >= coin){
            cashMap.put(player,cashMap.get(player) - coin);
            generalBank += coin;
            return true;
        }
        return false;
    }
}
