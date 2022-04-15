package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

public class BankHallObserver extends HallObserver{
    private final Map<Player,Integer> cashMap;
    private static final List<Integer> rewardPositions = Arrays.asList(3,6,9);
    private final Map<Place,Player> hallMap;
    private int generalBank;

    public BankHallObserver() {
        super();
        cashMap = new HashMap<>();
        hallMap = new HashMap<>();
        generalBank = Constants.MAX_NUM_OF_COINS;
    }

    @Override
    public void addPlayer(Player player) {
        super.addPlayer(player);
        hallMap.put(player.getSchool().getHallAsPlace(),player);
        cashMap.put(player,1);
        generalBank -= 1;
    }

    @Override
    public void update(Hall observable, PawnColor pawnColor) {
        super.update(observable, pawnColor);
        if(rewardPositions.contains(observable.getPawns().getFromColor(pawnColor))){
            reward(hallMap.get(observable));
        }
    }

    private void reward(Player player){
        if(generalBank > 0){
            cashMap.put(player,cashMap.get(player) + 1);
            generalBank -=1;
        }
    }

    public int getGeneralBank(){
        return generalBank;
    }

    public int getCashByPlayer(Player player){
        return cashMap.get(player);
    }
}
