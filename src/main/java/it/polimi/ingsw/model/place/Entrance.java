package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.Pawns;

public class Entrance extends SchoolPlace {
    @Override
    public boolean add(Pawns pawns) {
        if(super.getPawns().totalElements() + pawns.totalElements() > Constants.MaxStudentEntrance){
            return false;
        }
        return super.getPawns().addPawns(pawns);
    }
}

