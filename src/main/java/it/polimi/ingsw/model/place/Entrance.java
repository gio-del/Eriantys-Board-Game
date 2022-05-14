package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.Pawns;

/**
 * Entrance of the {@link School}
 */
public class Entrance extends SchoolPlace {
    private final int limit;

    public Entrance(int entranceLimit) {
        this.limit = entranceLimit;
    }

    /**
     * Add pawns to the current state of entrance
     *
     * @param pawns to be added
     * @return {@code true} if pawns was added, otherwise {@code false}
     */
    @Override
    public boolean add(Pawns pawns) {
        if (canBeAdded(pawns))
            return super.add(pawns);
        return false;
    }

    /**
     * @param pawns pawns asking if it can be moved
     * @return {@code true} if {@code pawns} can be added to the state, otherwise {@code false}.
     * But in every case doesn't add {@code pawns}
     */
    @Override
    public boolean canBeAdded(Pawns pawns) {
        return super.getPawns().totalElements() + pawns.totalElements() <= limit;
    }
}

