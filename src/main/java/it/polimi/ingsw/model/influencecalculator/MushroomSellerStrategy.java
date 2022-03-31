package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MushroomSellerStrategy extends StandardStrategy{
    PawnColor blockedColor;

    @Override
    public Map<Player, Integer> getScores(Island island, List<Player> players){
        Map<Player, Integer> scores = super.getScores(island,players);
        Optional<Player> blockedPlayer = Optional.empty();
        for(Player player: players){
            if(player.getSchool().getProfessorTable().getFromColor(blockedColor) == Constants.MaxProfessorPerColor)
                blockedPlayer = Optional.of(player);
        }
        blockedPlayer.ifPresent
                (player ->
                        scores.put(player,
                                scores.get(player) - island.getStudents().getFromColor(blockedColor)));
        return scores;
    }

    public void setBlockedColor(PawnColor blockedColor) {
        this.blockedColor = blockedColor;
    }
}
