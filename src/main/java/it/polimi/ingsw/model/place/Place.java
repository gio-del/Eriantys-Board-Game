package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.PawnColor;

public interface Place {

    boolean remove(PawnColor pawnColor);
    boolean add(PawnColor pawnColor);

}
