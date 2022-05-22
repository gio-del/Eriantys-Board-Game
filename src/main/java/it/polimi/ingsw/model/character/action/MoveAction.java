package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Place;

/**
 * Thi action is used to move a group of students from a place to another following the logic of the characters.
 */
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
        if (from.canBeRemoved(pawnsToBeMoved) && to.canBeAdded(pawnsToBeMoved)) {
            return from.remove(pawnsToBeMoved) && to.add(pawnsToBeMoved);
        }
        return false;
    }
}
