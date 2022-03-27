package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.*;

public class Cloud {
    private Pawns students;

    public Cloud() {
        this.students = new Pawns();
    }


    public Pawns getStudents(){
        return students;
    }

    public Pawns getStudentsAndRemove() {
        Pawns extracted = new Pawns();
        extracted.addPawns(this.students);
        this.students = new Pawns();
        return extracted;
    }

    public void fill(Pawns students) {
        this.students = students;
    }

}
