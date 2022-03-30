package it.polimi.ingsw.model.profassignment;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public interface ProfessorStrategy {
    List<Player> colorProfessorCheck(List<Player> players, PawnColor pawnColor);
}
