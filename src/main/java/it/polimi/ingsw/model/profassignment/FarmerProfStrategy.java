package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

public class FarmerProfStrategy implements ProfessorStrategy{
    private Player playertie;
    @Override
    public List<Player> colorProfessorCheck(List<Player> players, PawnColor pawnColor) {
        Map<Player, Integer> scores = new HashMap<>();
        for(Player player: players){
            int playerStudents = player.getHall().getFromColor(pawnColor);
            scores.put(player, playerStudents);
        }
        int winnervalue = (Collections.max(scores.values()));
        List<Player> winners = new ArrayList<>();
        if(scores.get(playertie) == winnervalue) {
            winners.add(playertie);
        } else {
            for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
                if (entry.getValue().equals(winnervalue))
                    winners.add(entry.getKey());
            }
        }
        return winners;
    }

    public void setPlayertie(Player playertie){
        this.playertie = playertie;
    }
}
