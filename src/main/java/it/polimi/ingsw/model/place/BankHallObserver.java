package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.Bank;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

public class BankHallObserver extends HallObserver{
    private static final List<Integer> rewardPositions = Arrays.asList(3,6,9);
    private final Map<Place,Player> hallMap;
    private final Bank bank;

    public BankHallObserver(Bank bank) {
        super();
        this.bank = bank;
        hallMap = new HashMap<>();
    }

    @Override
    public void addPlayer(Player player) {
        super.addPlayer(player);
        hallMap.put(player.getSchool().getHallAsPlace(),player);
        bank.initPlayer(player);
    }

    @Override
    public void update(Hall observable, PawnColor pawnColor) {
        super.update(observable, pawnColor);
        if(rewardPositions.contains(observable.getPawns().getFromColor(pawnColor))){
            reward(hallMap.get(observable));
        }
    }

    private void reward(Player player){
        bank.reward(player);
    }
}
