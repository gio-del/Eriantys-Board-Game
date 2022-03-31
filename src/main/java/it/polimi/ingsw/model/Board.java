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
    private final InfluenceStrategy standardStrategy = new StandardStrategy();

    public Board(){
        this.islands = new ArrayList<>(Constants.MAX_ISLAND);
        for(int i = 0; i < Constants.MAX_ISLAND; i++){
            islands.add(new Island());
        }
        this.influenceStrategy = new StandardStrategy();

    }

    public void initIslands(Sack sack) {
        setMotherNaturePos(0);
        for(int i = 1; i < Constants.MAX_ISLAND; i++){
            if (i!=6) {
                islands.get(i).addStudent(sack.extract());
            }

        }
    }

    public Optional<TowerColor> moveMotherNature(int steps, List<Player> players){
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

        int winnervalue = (Collections.max(score.values()));
        List<Player> winners = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : score.entrySet())
        {
            if (entry.getValue().equals(winnervalue))
                winners.add(entry.getKey());
        }
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
            island.getTower().ifPresent(towerColor -> towerColorList.add(towerColor));
        }
    }

    public void setStrategy(InfluenceStrategy influenceStrategy){
        this.influenceStrategy = influenceStrategy;
    }

    public void resetStrategy(){
        this.influenceStrategy = standardStrategy;
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
