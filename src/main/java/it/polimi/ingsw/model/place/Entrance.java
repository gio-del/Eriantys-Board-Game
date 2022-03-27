package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

public class Entrance implements Place {
    private Pawns students;

    public Entrance() {
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
        if (students.getFromColor(pawnColor)<7) {  //TODO: implement 9 student for 3 player
            students.addColor(pawnColor);
            return true;
        }
        return false;
    }
}

