package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class ThiefAction implements Action {
    private final PawnColor colorToSteal;
    private final List<Player> players;
    private final int numberOfRemovedStudents;

    public ThiefAction(PawnColor colorToSteal, List<Player> players, int numberOfRemovedStudents) {
        this.colorToSteal = colorToSteal;
        this.players = players;
        this.numberOfRemovedStudents = numberOfRemovedStudents;
    }

    @Override
    public boolean apply() {
        //TODO: color must return in the sack
        players.forEach(player ->
                player.getSchool().getHall().
                        removeColor(colorToSteal,
                                Math.min(numberOfRemovedStudents,
                                        player.getSchool().getHall().getFromColor(colorToSteal))));
        return true;
    }

}
