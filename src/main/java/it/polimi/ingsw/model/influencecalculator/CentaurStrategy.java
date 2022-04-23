package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Centaur Strategy doesn't count tower in the influence calc
 */
public class CentaurStrategy extends StandardStrategy{
    @Override
    public Map<Player, Integer> getScores(Island island, List<Player> players){
        Map<Player, Integer> scores = super.getScores(island,players);
        for(Player player: players){
            if(island.getTower().isPresent() && island.getTower().equals(Optional.of(player.getColor())))
                scores.put(player, scores.get(player) - island.getDimension());
        }
        return scores;
    }
}
