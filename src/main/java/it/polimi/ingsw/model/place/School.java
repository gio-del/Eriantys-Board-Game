package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.Pawns;

public class School {
    private Entrance entrance;
    private Hall hall;
    private ProfTable profTable;

    public School() {
        this.entrance = new Entrance();
        this.hall= new Hall();
        this.profTable = new ProfTable();
    }

    public Pawns getEntrance() {
        return entrance.getStudents();
    }

    public Pawns getHall() {
        return hall.getStudents();
    }

    public Pawns getProfessorTable() {
        return profTable.getProfessors();
    }
}
