package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;


/**
 * StandardStrategy is the default strategy used in the calc of the influence on a given island
 */
public class StandardStrategy implements InfluenceStrategy{
    @Override
    public Map<Player, Integer> getScores(Island island, List<Player> players) {
        Map<Player, Integer> scores = new HashMap<>();
        for(Player player : players){
            int value = 0;
            for(PawnColor pawnColor: PawnColor.values()){
                if(player.getSchool().getProfessorTable().getFromColor(pawnColor) == 1){
                    value = value + island.getStudents().getFromColor(pawnColor);
                }
            }
            if(island.getTower()!=null && island.getTower().equals(player.getColor())) {
                value = value + island.getDimension();
            }
            scores.put(player, value);
        }
        return scores;
    }
}
