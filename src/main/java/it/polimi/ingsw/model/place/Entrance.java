package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.Pawns;

/**
 * Entrance of the {@link School}
 */
public class Entrance extends SchoolPlace {

    /**
     * Add pawns to the current state of entrance
     * @param pawns to be added
     * @return {@code true} if pawns was added, otherwise {@code false}
     */
    @Override
    public boolean add(Pawns pawns) {
        if(canBeMoved(pawns))
            return super.add(pawns);
        return false;
    }

    /**
     *
     * @param pawns
     * @return {@code true} if {@code pawns} can be added to the state, otherwise {@code false}.
     * But in every case doesn't add {@code pawns}
     */
    @Override
    public boolean canBeMoved(Pawns pawns) {
        return super.getPawns().totalElements() + pawns.totalElements() <= Constants.MaxStudentEntrance;
    }
}

