package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

public class Hall implements Place {
    private Pawns students;

    public Hall() {
        this.students = new Pawns();
    }

    public Pawns getStudents() {
        return students;
    }

    @Override
    public boolean remove(PawnColor pawnColor) {
        if (students.getFromColor(pawnColor)>0) {
            students.removeColor(pawnColor);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(PawnColor pawnColor) {
        if (students.getFromColor(pawnColor)<10) {   //TODO: implement notify for coin (3,6,9 students)
            students.addColor(pawnColor);
            return true;
        }
        return false;
    }
}
