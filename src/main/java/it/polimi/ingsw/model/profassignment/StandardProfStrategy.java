package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

/**
 * The Standard Professor Assignment strategy
 */
public class StandardProfStrategy implements ProfessorStrategy {
    @Override
    public List<Player> colorProfessorCheck(List<Player> players, PawnColor pawnColor) {
        Map<Player, Integer> scores = new HashMap<>();
        for (Player player : players) {
            int playerStudents = player.getSchool().getHall().getFromColor(pawnColor);
            scores.put(player, playerStudents);
        }
        int winnerValue = (Collections.max(scores.values()));
        List<Player> winners = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            if (entry.getValue().equals(winnerValue))
                winners.add(entry.getKey());
        }
        return winners;
    }

}
