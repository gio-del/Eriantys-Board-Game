package it.polimi.ingsw.model.place;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;

public class ProfTable extends SchoolPlace {

    @Override
    public boolean add(PawnColor pawnColor) {
        if (super.getPawns().getFromColor(pawnColor) < Constants.MaxProfessorPerColor) {
            return super.add(pawnColor);
        }
        return false;
    }
}
