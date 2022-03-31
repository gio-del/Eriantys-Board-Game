package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.Pawns;

public interface Place {
// TODO: add getter Pawns there
    boolean remove(Pawns pawns);
    boolean add(Pawns pawns);
    boolean canBeMoved(Pawns pawns);
    boolean canBeRemoved(Pawns pawns);

}
