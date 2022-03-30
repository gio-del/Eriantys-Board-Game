package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MushroomSellerStrategy implements InfluenceStrategy{
    PawnColor blockedColor;
    @Override
    public Map<Player, Integer> getScores(Island island, List<Player> players){
        Map<Player, Integer> scores = new HashMap<>();

        for(Player player : players){
            int value = 0;
            for(PawnColor pawnColor: PawnColor.values()){
                if(pawnColor != blockedColor){
                    if(player.getProfTable().getFromColor(pawnColor) == 1){
                        value = value + island.getStudents().getFromColor(pawnColor);
                    }
                }
            }
            if(island.getTower().isPresent() && island.getTower().equals(Optional.of(player.getColor()))) {
                value = value + island.getDimension();
            }
            scores.put(player, value);
        }
        return scores;
    }

    public void setBlockedColor(PawnColor blockedColor) {
        this.blockedColor = blockedColor;
    }
}
