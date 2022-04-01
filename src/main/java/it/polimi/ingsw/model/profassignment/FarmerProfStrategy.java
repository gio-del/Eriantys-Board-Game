package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.*;

public class FarmerProfStrategy extends StandardProfStrategy{
    private final Player playerTie;

    public FarmerProfStrategy(Player playerTie) {
        this.playerTie = playerTie;
    }

    @Override
    public List<Player> colorProfessorCheck(List<Player> players, PawnColor pawnColor) {
        List<Player> winners = super.colorProfessorCheck(players,pawnColor);
        if(winners.contains(playerTie))
            return List.of(playerTie);
        return null;
    }
}
