package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

public class FarmerProfStrategy implements ProfessorStrategy{
    private final Player playerTie;

    public FarmerProfStrategy(Player playerTie) {
        this.playerTie = playerTie;
    }

    @Override
    public List<Player> colorProfessorCheck(List<Player> players, PawnColor pawnColor) {
        Map<Player, Integer> scores = new HashMap<>();
        for(Player player: players){
            int playerStudents = player.getSchool().getHall().getFromColor(pawnColor);
            scores.put(player, playerStudents);
        }
        int winnervalue = (Collections.max(scores.values()));
        List<Player> winners = new ArrayList<>();
        if(scores.get(playerTie) == winnervalue) {
            winners.add(playerTie);
        } else {
            for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
                if (entry.getValue().equals(winnervalue))
                    winners.add(entry.getKey());
            }
        }
        return winners;
    }

}
