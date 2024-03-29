package it.polimi.ingsw.model.influencecalculator;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.player.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The MushroomSeller Strategy blocks a color during the calc of the influence
 */
public class MushroomSellerStrategy extends StandardStrategy {
    private final PawnColor blockedColor;

    public MushroomSellerStrategy(PawnColor blockedColor) {
        this.blockedColor = blockedColor;
    }

    @Override
    public Map<Player, Integer> getScores(Island island, List<Player> players) {
        Map<Player, Integer> scores = super.getScores(island, players);
        Optional<Player> blockedPlayer = Optional.empty();
        for (Player player : players) {
            if (player.getSchool().getProfessorTable().getFromColor(blockedColor) == Constants.MAX_PROFESSOR_PER_COLOR)
                blockedPlayer = Optional.of(player);
        }
        blockedPlayer.ifPresent
                (player ->
                        scores.put(player,
                                scores.get(player) - island.getStudents().getFromColor(blockedColor)));
        return scores;
    }
}
