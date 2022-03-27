package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

public class ProfTable implements Place {
    private Pawns professors;

    public ProfTable() {
        this.professors = new Pawns();
    }

    public Pawns getProfessors() {
        return professors;
    }

    @Override
    public boolean remove(PawnColor pawnColor) {
        if (professors.getFromColor(pawnColor)>0) {
            professors.removeColor(pawnColor);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(PawnColor pawnColor) {
        if (professors.getFromColor(pawnColor)<1) {  //TODO: consider professor assignment strtegy
            professors.addColor(pawnColor);
            return true;
        }
        return false;
    }
}
