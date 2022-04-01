package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Island;

public class CalculateInfluenceAction implements Action{
    private final Island island;

    public CalculateInfluenceAction(Island island) {
        this.island = island;
    }

    @Override
    public boolean apply() {
        /* TODO: this need a method in Board that let me call the calc of influence on a given Island*/
        // board.calcInfluence(island); something like that
        return false;
    }
}
