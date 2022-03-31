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

    public Place getProfTableAsPlace(){
        return profTable;
    }


    public boolean addProfessor(PawnColor pawnColor) {
        return profTable.add(new Pawns(pawnColor));
    }

    public boolean removeProfessor(PawnColor pawnColor) {
        return profTable.remove(new Pawns(pawnColor));
    }

    public boolean addStudentInHall(PawnColor pawnColor) {
        Pawns pawns = new Pawns(pawnColor);
        return hall.add(pawns);
    }

    public boolean addStudentInHall(Pawns pawns) {
        return hall.add(pawns);
    }

    public boolean removeStudentFromHall(PawnColor pawnColor) {
        // TODO: is this necessary?
        return hall.remove(new Pawns(pawnColor));
    }

    public boolean removeStudentFromHall(Pawns pawns) {
        return hall.remove(pawns);
    }

    public boolean moveStudentToHall(PawnColor pawnColor) {
        // TODO: is this necessary?
        Pawns pawns = new Pawns(pawnColor);
        if(entrance.remove(pawns)) {
            hall.add(pawns);
            return true;
        }
        return false;
    }

    public boolean moveStudentToHall(Pawns pawns) {
        if(entrance.remove(pawns)){
            hall.add(pawns);
            return true;
        }
        return false;
    }

    public boolean removeStudentFromEntrance(PawnColor pawnColor) {
        // TODO: is this necessary?
        return entrance.remove(new Pawns(pawnColor));
    }

    public boolean removeStudentFromEntrance(Pawns pawns) {
        // TODO: is this necessary?
        return entrance.remove(pawns);
    }

    public boolean addStudentInEntrance(PawnColor pawnColor) {
        return entrance.add(new Pawns(pawnColor));
    }

    public boolean addStudentInEntrance(Pawns pawns) {
        return entrance.add(pawns);
    }
}
