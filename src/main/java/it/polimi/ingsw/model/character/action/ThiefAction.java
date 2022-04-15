package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class ThiefAction implements Action {
    private final PawnColor colorToSteal;
    private final List<Player> players;

    public ThiefAction(PawnColor colorToSteal, List<Player> players) {
        this.colorToSteal = colorToSteal;
        this.players = players;
    }

    @Override
    public boolean apply() {
        players.forEach(player ->
                        player.getSchool().getHall().
                                removeColor(colorToSteal,
                                        Math.min(3,
                                                player.getSchool().getHall().getFromColor(colorToSteal))));
        return true;
    }

}
