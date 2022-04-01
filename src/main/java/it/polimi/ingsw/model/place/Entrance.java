package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.Pawns;

public class Entrance extends SchoolPlace {
    @Override
    public boolean add(Pawns pawns) {
        if(canBeMoved(pawns))
            return super.add(pawns);
        return false;
    }

    @Override
    public boolean canBeMoved(Pawns pawns) {
        return super.getPawns().totalElements() + pawns.totalElements() <= Constants.MaxStudentEntrance;
    }
}

