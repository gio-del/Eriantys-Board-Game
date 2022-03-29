package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.Pawns;

public interface Place {

    boolean remove(Pawns pawns);
    boolean add(Pawns pawns);

}
