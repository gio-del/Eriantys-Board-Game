package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;


import java.util.List;
import java.util.Map;

/**
 * The knight strategy gives two bonus points to the player who played the Knight {@link it.polimi.ingsw.model.character.CharacterCard}
 */
public class KnightStrategy extends StandardStrategy {
    final TowerColor towerPlus;

    public KnightStrategy(TowerColor towerPlus) {
        this.towerPlus = towerPlus;
    }

    @Override
    public Map<Player, Integer> getScores(Island island, List<Player> players){
        Map<Player, Integer> scores = super.getScores(island,players);
        for(Player player: players){
            if(player.getColor() == towerPlus){
                scores.put(player,2 + scores.get(player));
            }
        }
        return scores;
    }
}
