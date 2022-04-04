package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

/**
 * The Professors Table of the {@link School}
 */
public class ProfTable extends SchoolPlace {
    @Override
    public boolean add(Pawns pawns) {
        if(canBeMoved(pawns))
            return super.add(pawns);
        return false;
    }

    @Override
    public boolean canBeMoved(Pawns pawns) {
        for(PawnColor pawnColor: PawnColor.values()){
            if(super.getPawns().getFromColor(pawnColor) + pawns.getFromColor(pawnColor) > Constants.MAX_PROFESSOR_PER_COLOR){
                return false;
            }
        }
        return true;
    }
}
