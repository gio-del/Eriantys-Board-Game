package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.Collections;
import java.util.List;

/**
 * The Farmer strategy let the player who used the Farmer {@link it.polimi.ingsw.model.character.CharacterCard}
 * win even if is involved in a tie
 */
public class FarmerProfStrategy extends StandardProfStrategy {
    private final Player playerTie;

    public FarmerProfStrategy(Player playerTie) {
        this.playerTie = playerTie;
    }

    @Override
    public List<Player> colorProfessorCheck(List<Player> players, PawnColor pawnColor) {
        List<Player> winners = super.colorProfessorCheck(players, pawnColor);
        if (winners.contains(playerTie))
            return List.of(playerTie);
        return Collections.emptyList();
    }
}
