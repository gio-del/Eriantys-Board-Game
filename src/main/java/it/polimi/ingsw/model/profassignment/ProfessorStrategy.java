package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * This interface is used to implement strategy pattern in the assignment of professor
 */
public interface ProfessorStrategy {
    List<Player> colorProfessorCheck(List<Player> players, PawnColor pawnColor);
}
