package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.place.Place;
import it.polimi.ingsw.model.pawns.Pawns;

public class MoveAction implements Action {
    private final Place from;
    private final Place to;
    private final Pawns pawnsToBeMoved;

    public MoveAction(Place from, Place to, Pawns pawnsToBeMoved) {
        this.from = from;
        this.to = to;
        this.pawnsToBeMoved = pawnsToBeMoved;
    }

    @Override
    public boolean apply() {
        if(from.canBeRemoved(pawnsToBeMoved) && to.canBeMoved(pawnsToBeMoved)) {
            return from.remove(pawnsToBeMoved) && to.add(pawnsToBeMoved);
        }
        return false;
    }
}
