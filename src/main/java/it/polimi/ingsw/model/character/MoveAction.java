package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Place;
import it.polimi.ingsw.model.pawns.Pawns;

public class MoveAction implements Action{
    private final Place from, to;
    private final Pawns pawnsToBeMoved;

    public MoveAction(Place from, Place to, Pawns pawnsToBeMoved) {
        this.from = from;
        this.to = to;
        this.pawnsToBeMoved = pawnsToBeMoved;
    }

    @Override
    public boolean doAction() {
        // TODO
        return false;
    }
}
