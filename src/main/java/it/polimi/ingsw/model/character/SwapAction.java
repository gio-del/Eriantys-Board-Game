package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.place.Place;
import it.polimi.ingsw.model.pawns.Pawns;

public class SwapAction implements Action{
    private final Place from, to;
    private final Pawns toBeSwappedFrom, toBeSwappedTo;

    public SwapAction(Place from, Place to, Pawns toBeSwappedFrom, Pawns toBeSwappedTo) {
        this.from = from;
        this.to = to;
        this.toBeSwappedFrom = toBeSwappedFrom;
        this.toBeSwappedTo = toBeSwappedTo;
    }

    @Override
    public boolean doAction() {
        // TODO
        return false;
    }
}
