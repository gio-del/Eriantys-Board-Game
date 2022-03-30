package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class KnightStrategy extends StandardStrategy {
    TowerColor towerplus;
    @Override

    public Map<Player, Integer> getScores(Island island, List<Player> players){
        Map<Player, Integer> scores = new HashMap<>();

        for(Player player : players){
            int value = 0;
            for(PawnColor pawnColor: PawnColor.values()){
                if(player.getProfTable().getFromColor(pawnColor) == 1){
                    value = value + island.getStudents().getFromColor(pawnColor);
                }
            }
            if(island.getTower().isPresent() && island.getTower().equals(Optional.of(player.getColor()))) {
                value = value + island.getDimension();
            }
            if(player.getColor().equals(towerplus)){
                value = value + 2;
            }
            scores.put(player, value);
        }
        return scores;
    }

    public void setTowerplus(TowerColor playerplus) {
        this.towerplus = playerplus;
    }
}
