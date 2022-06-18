package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Sack;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * This action is used to steal students from the hall of the player following characters' logic.
 */
public class ThiefAction implements Action {
    private final PawnColor colorToSteal;
    private final List<Player> players;
    private final int numOfStudentsToRemove;
    private final Sack sack;

    public ThiefAction(PawnColor colorToSteal, List<Player> players, int numOfStudentsToRemove, Sack sack) {
        this.colorToSteal = colorToSteal;
        this.players = players;
        this.numOfStudentsToRemove = numOfStudentsToRemove;
        this.sack = sack;
    }

    @Override
    public boolean apply() {
        int numOfRemovedStudents = 0;
        for (Player player : players) {
            int toRemove = Math.min(numOfStudentsToRemove, player.getSchool().getHall().getFromColor(colorToSteal));
            numOfRemovedStudents += toRemove;
            player.getSchool().getHall().
                    removeColor(colorToSteal,
                            toRemove);
        }
        Pawns toReturn = new Pawns();
        toReturn.addColor(colorToSteal, numOfRemovedStudents);
        sack.returnTo(toReturn);
        return true;
    }

}
