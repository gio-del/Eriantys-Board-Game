package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

public class Hall extends SchoolPlace {
    @Override
    public boolean add(Pawns pawns) {
        for(PawnColor pawnColor: PawnColor.values()){
            if(super.getPawns().getFromColor(pawnColor) + pawns.getFromColor(pawnColor) > Constants.MaxStudentHallPerColor){
                return false;
            }
        }
        return super.getPawns().addPawns(pawns);
    }
}
