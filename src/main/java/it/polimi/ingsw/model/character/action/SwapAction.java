package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.place.Place;
import it.polimi.ingsw.model.pawns.Pawns;

public class SwapAction implements Action {
    private final Place from;
    private final Place to;
    private final Pawns toBeSwappedFrom;
    private final Pawns toBeSwappedTo;

    public SwapAction(Place from, Place to, Pawns toBeSwappedFrom, Pawns toBeSwappedTo) {
        this.from = from;
        this.to = to;
        this.toBeSwappedFrom = toBeSwappedFrom;
        this.toBeSwappedTo = toBeSwappedTo;
    }

    @Override
    public boolean apply() {
        if(toBeSwappedFrom.totalElements() == toBeSwappedTo.totalElements())
            return from.add(toBeSwappedTo) &&
                    from.remove(toBeSwappedFrom) &&
                    to.add(toBeSwappedFrom) &&
                    to.remove(toBeSwappedTo);
        return false;
    }
}
