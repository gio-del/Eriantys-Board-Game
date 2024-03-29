package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.influencecalculator.InfluenceStrategy;
import it.polimi.ingsw.model.influencecalculator.StandardStrategy;
import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.observer.WinObservable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents the Board with the islands inside
 * Knows where's MotherNature and knows the strategies to calculate the influences
 */
public class Board extends WinObservable {
    private final List<Island> islands;
    private int motherNaturePos;
    private InfluenceStrategy influenceStrategy;

    /**
     * Initiate the board with 12 Islands, set Strategy at standard and motherNature
     * on the first island of the ArrayList
     */
    public Board() {
        this.islands = new ArrayList<>(Constants.MAX_ISLAND);
        for (int i = 0; i < Constants.MAX_ISLAND; i++) {
            islands.add(new Island());
        }
        this.influenceStrategy = new StandardStrategy();
        this.motherNaturePos = 0;
    }

    /**
     * @param sack from which the island will draw the pawns
     */
    public void initIslands(Sack sack) {
        for (int i = 1; i < Constants.MAX_ISLAND; i++) {
            if (i != 6) {
                islands.get(i).add(sack.extract());
            }
        }
    }


    /**
     * Used to calculate if some adjacent islands as the same tower.
     */
    public void adjacencyUpdate() {
        int i = 0;
        while (i < islands.size()) {
            if (i == islands.size() - 1) {
                if (islands.get(i).getTower() != null && islands.get(i).getTower().equals(islands.get(0).getTower())) {
                    islands.get(0).add(islands.get(i).getStudents());
                    islands.get(0).upgradeDimension(islands.get(i).getDimension());
                    islands.get(0).upgradeBanTiles(islands.get(i).getBanTiles());
                    if (motherNaturePos == islands.size() - 1) motherNaturePos = 0;
                    islands.remove(i);
                }
            } else {
                if (islands.get(i).getTower() != null && islands.get(i).getTower().equals(islands.get(i + 1).getTower())) {
                    islands.get(i).add(islands.get(i + 1).getStudents());
                    islands.get(i).upgradeDimension(islands.get(i + 1).getDimension());
                    islands.get(i).upgradeBanTiles(islands.get(i + 1).getBanTiles());
                    if (motherNaturePos == i + 1) motherNaturePos = i;
                    islands.remove(i + 1);
                    i--;
                }
            }
            i++;
        }
        notifyObserver(obs -> obs.updateBoardUsage(islands.size()));
    }

    /**
     * Move mother nature, trigger influence calc on island where she stops
     *
     * @param steps to move mother nature
     */
    public void moveMotherNature(int steps) {
        int index = motherNaturePos;
        if ((index + steps) > islands.size() - 1) {
            motherNaturePos = index + steps - islands.size();
        } else {
            motherNaturePos = index + steps;
        }
    }

    /**
     * Calculate influence on a given island
     *
     * @param island  on which to calc influence
     * @param players players in game
     * @return the TowerColor of the winner if present, null otherwise
     */
    public TowerColor calculateInfluence(Island island, List<Player> players) {
        Map<Player, Integer> scores = influenceStrategy.getScores(island, players);
        List<Player> winners = getWinners(scores);
        TowerColor winner = island.conquerIsland(winners, players);
        if (winner != null) {
            adjacencyUpdate();
            notifyObserver(obs -> obs.updateTowerPlaced(winner));
        }
        return winner;
    }

    /**
     * Calculate influence on the island where mother nature stopped
     *
     * @param players players in game
     * @return the TowerColor of the winner if present, null otherwise
     */
    public TowerColor calculateInfluence(List<Player> players) {
        return calculateInfluence(islands.get(motherNaturePos), players);
    }

    /**
     * private to calculate the winners from the scores
     *
     * @param scores for each player
     * @return the player or players with the highest points
     */

    private List<Player> getWinners(Map<Player, Integer> scores) {
        int winnerValue = (Collections.max(scores.values()));
        List<Player> winners = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : scores.entrySet())
            if (entry.getValue().equals(winnerValue))
                winners.add(entry.getKey());
        return winners;
    }

    public int numberOfIslands() {
        return islands.size();
    }

    /**
     * Method used to change the actual strategy
     *
     * @param influenceStrategy the concrete strategy to be created
     */
    public void setStrategy(InfluenceStrategy influenceStrategy) {
        this.influenceStrategy = influenceStrategy;
    }

    /**
     * reset the influenceStrategy at Standard
     */
    public void resetStrategy() {
        this.influenceStrategy = new StandardStrategy();
    }

    /**
     * return the strategy to calculate the influences during this turn
     *
     * @return strategy of the turn
     */
    public InfluenceStrategy getInfluenceStrategy() {
        return influenceStrategy;
    }

    /**
     * Return List of islands
     *
     * @return list of islands
     */
    public List<Island> getIslands() {
        return islands;
    }

    /**
     * Get island of mother nature
     *
     * @return island with mother nature
     */
    public Island getMotherNatureIsland() {
        return islands.get(motherNaturePos);
    }

    /**
     * @return the actual position of MotherNature
     */
    public int getMotherNaturePos() {
        return motherNaturePos;
    }

    /**
     * set the position of motherNature for the tests
     *
     * @param motherNaturePos index of the island
     */
    public void setMotherNaturePos(int motherNaturePos) {
        this.motherNaturePos = motherNaturePos;
    }
}
