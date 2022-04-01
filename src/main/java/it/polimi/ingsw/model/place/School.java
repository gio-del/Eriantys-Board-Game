package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

public class School {
    private final SchoolPlace entrance;
    private final SchoolPlace hall;
    private final SchoolPlace profTable;

    public School() {
        this.entrance = new Entrance();
        this.hall= new Hall();
        this.profTable = new ProfTable();
    }

    public Pawns getEntrance() {
        return entrance.getPawns();
    }

    public Pawns getHall() {
        return hall.getPawns();
    }

    public Pawns getProfessorTable() {
        return profTable.getPawns();
    }

    public Place getEntranceAsPlace(){
        return entrance;
    }

    public Place getHallAsPlace(){
        return hall;
    }

    public boolean addProfessor(PawnColor pawnColor) {
        return profTable.add(new Pawns(pawnColor));
    }

    public boolean removeProfessor(PawnColor pawnColor) {
        return profTable.remove(new Pawns(pawnColor));
    }

    public boolean addStudentInHall(Pawns pawns) {
        return hall.add(pawns);
    }

    public boolean removeStudentFromHall(Pawns pawns) {
        return hall.remove(pawns);
    }

    public boolean moveStudentToHall(Pawns pawns) {
        if(entrance.canBeRemoved(pawns) && hall.canBeMoved(pawns)) {
            entrance.remove(pawns);
            hall.add(pawns);
            return true;
        }
        return false;
    }

    public boolean removeStudentFromEntrance(Pawns pawns) {
        // TODO: is this necessary?
        return entrance.remove(pawns);
    }

    public boolean addStudentInEntrance(Pawns pawns) {
        return entrance.add(pawns);
    }

}
