package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;

public class Entrance extends SchoolPlace {

    @Override
    public boolean add(PawnColor pawnColor) {
        if (super.getPawns().getFromColor(pawnColor) < Constants.MaxStudentEntrance) {  //TODO: implement 9 student for 3 player
            return super.add(pawnColor);
        }
        return false;
    }

    @Override
    public boolean remove(PawnColor pawnColor) {
        if (super.getPawns().getFromColor(pawnColor) > 0) {
            return super.remove(pawnColor);
        }
        return false;
    }
}

