package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.influencecalculator.InfluenceStrategy;
import it.polimi.ingsw.model.influencecalculator.StandardStrategy;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;

import java.util.*;

public class Board {
    private ArrayList<Island> islands;
    private int motherNaturePos;
    private InfluenceStrategy influenceStrategy;

    public Board(){
        this.islands = new ArrayList<>(Constants.MAX_ISLAND);
        for(int i = 0; i < Constants.MAX_ISLAND; i++){
            islands.add(new Island());
        }
        this.influenceStrategy = new StandardStrategy();
        this.motherNaturePos = 0;

    }

    public void initIslands(Sack sack) {
        // TODO: this must be called in constructor or separated?
        for(int i = 1; i < Constants.MAX_ISLAND; i++){
            if (i!=6) {
                islands.get(i).add(sack.extract());
            }
        }
    }

    public TowerColor moveMotherNature(int steps, List<Player> players){
        int index = motherNaturePos;
        if((index + steps) > islands.size() - 1){
            motherNaturePos = index + steps - islands.size();
        } else{
            motherNaturePos = index + steps;
        }
        Island island = islands.get(motherNaturePos);
        Map<Player, Integer> scores = influenceStrategy.getScores(island, players);
        List<Player> winners = getWinners(scores);
        return island.conquerIsland(winners, players);
    }

    private List<Player> getWinners(Map<Player, Integer> score){
        int winnerValue = (Collections.max(score.values()));
        List<Player> winners = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : score.entrySet())
            if (entry.getValue().equals(winnerValue))
                winners.add(entry.getKey());
        return winners;
    }

    public int numberOfIslands(){
        return islands.size();
    }

    public void useIsland(Island island){
        // TODO
    }

    public void adjacenciesUpdate(){
        // TODO
        List<TowerColor> towerColorList = new ArrayList<>();
        for(Island island: islands){
            island.getTower().ifPresent(towerColorList::add);
        }
    }

    public void setStrategy(InfluenceStrategy influenceStrategy){
        this.influenceStrategy = influenceStrategy;
    }

    public void resetStrategy(){
        this.influenceStrategy = new StandardStrategy();
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public Island getSpecificIsland(int index) {
        return islands.get(index);
    }

    public void setMotherNaturePos(int motherNaturePos) {
        this.motherNaturePos = motherNaturePos;
    }

    public int getMotherNaturePos() {
        return motherNaturePos;
    }
}
