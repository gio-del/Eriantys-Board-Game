package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.Pawns;

/**
 * A Class implements this interface if it can have Pawns on it
 */
public interface Place {

    /**
     * @param pawns to remove
     * @return {@code true} if it was correctly removed, otherwise {@code false}
     */
    boolean remove(Pawns pawns);

    /**
     * @param pawns to add
     * @return {@code true} if it was correctly added, otherwise {@code false}
     */
    boolean add(Pawns pawns);

    /**
     * @param pawns to be added
     * @return {@code true} if it can be added, otherwise {@code false}
     */
    boolean canBeAdded(Pawns pawns);

    /**
     * @param pawns to be removed
     * @return {@code true} if it can be removed, otherwise {@code false}
     */
    boolean canBeRemoved(Pawns pawns);

}
