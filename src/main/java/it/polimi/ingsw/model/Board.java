package it.polimi.ingsw.model;

import it.polimi.ingsw.model.influencecalculator.InfluenceStrategy;
import it.polimi.ingsw.model.player.TowerColor;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private ArrayList<Island> islands;
    private int motherNaturePos;
    private InfluenceStrategy influenceStrategy;
    public static final int MAX_ISLAND = 12;

    public Board(){
        this.islands = new ArrayList<>(MAX_ISLAND);
    }

    public Island moveMotherNature(int steps){
        //TODO
        return null;
    }

    public int numberOfIslands(){
        return islands.size();
    }

    public void useIsland(Island island){
        // TODO
    }

    public void adjacenciesUpdate(){
        List<TowerColor> towerColorList = new ArrayList<>();
        for(Island island: islands){
            island.getTower().ifPresent(towerColor -> towerColorList.add(towerColor));
        }
    }

    public boolean resetStrategy(){
        // TODO
        return true;
    }
}
