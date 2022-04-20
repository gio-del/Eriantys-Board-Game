package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class CalculateInfluenceAction implements Action {
    private final List<Player> players; //this comes from the controller
    private final Board board; //this comes from the controller
    private final Island island; //this is the only user's input

    public CalculateInfluenceAction(List<Player> players, Board board, Island island) {
        this.players = players;
        this.board = board;
        this.island = island;
    }

    @Override
    public boolean apply() {
        board.calculateInfluence(island,players);
        return true;
    }
}
