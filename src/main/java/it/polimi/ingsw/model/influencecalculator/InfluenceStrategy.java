package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Map;

/**
 * Interface used to implement strategy pattern to calculate influence on a given island
 */
public interface InfluenceStrategy {

    /**
     * This method is called whenever mother nature stops on an island
     * @param island the island to calc influence on
     * @param players the list of players in the game
     * @return the influence of each player on the island calculated with the appropriate strategy
     */
    Map<Player, Integer> getScores(Island island, List<Player> players);
}
