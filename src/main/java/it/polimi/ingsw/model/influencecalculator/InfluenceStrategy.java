package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Map;

public interface InfluenceStrategy {
    Map<Player, Integer> getScores(Island island, List<Player> players);
}
