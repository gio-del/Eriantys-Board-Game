package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

public abstract class SchoolPlace implements Place{
    private Pawns students;

    public SchoolPlace() {
        this.students = new Pawns();
    }

    public Pawns getPawns() {
        return students;
    }

    @Override
    public boolean remove(PawnColor pawnColor) {
        return students.removeColor(pawnColor);
    }

    @Override
    public boolean add(PawnColor pawnColor) {
        return students.addColor(pawnColor);
    }

}
