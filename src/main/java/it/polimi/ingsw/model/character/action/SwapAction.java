package it.polimi.ingsw.model.character.action;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Place;

import java.util.List;

public class SwapAction implements Action {
    private final Place from;
    private final Place to;
    private final List<PawnColor> swapList;

    public SwapAction(Place from, Place to, List<PawnColor> swapList) {
        this.from = from;
        this.to = to;
        this.swapList = swapList;
    }

    @Override
    public boolean apply() {
        int i = 0;
        Pawns toBeRemovedFrom = new Pawns();
        Pawns toBeRemovedTo = new Pawns();
        if (swapList.size() % 2 != 0) return false; //todo: add control on number of swaps!
        while (i < swapList.size()) {
            toBeRemovedFrom.addColor(swapList.get(i));
            toBeRemovedTo.addColor(swapList.get(i + 1));
            i += 2;
        }
        if (from.canBeRemoved(toBeRemovedFrom) && to.canBeRemoved(toBeRemovedTo)) {
            return from.remove(toBeRemovedFrom) &&
                    from.add(toBeRemovedTo) &&
                    to.remove(toBeRemovedTo) &&
                    to.add(toBeRemovedFrom);
        }
        return false;
    }
}
