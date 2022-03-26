package it.polimi.ingsw.model;

import it.polimi.ingsw.model.influencecalculator.InfluenceStrategy;

import java.util.ArrayList;

public class Board {
    private ArrayList<Island> islands;
    private int motherNaturePos;
    private InfluenceStrategy influenceStrategy;
    public static final int MAX_ISLAND = 12;

    public Board(){
        this.islands = new ArrayList<>(MAX_ISLAND);
    }

    public Island moveMotherNature(int steps){

    }

    public int numberOfIslands(){

    }

    public void useIsland(Island island){

    }

    public boolean adjacenciesUpdate(){

    }

    public boolean resetStratey(){

    }
}
