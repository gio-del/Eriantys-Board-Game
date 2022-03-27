package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

public class School {
    private SchoolPlace entrance;
    private SchoolPlace hall;
    private SchoolPlace profTable;

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

    public boolean addProfessor(PawnColor pawnColor){
        return profTable.add(pawnColor);
    }

    public boolean removeProfessor(PawnColor pawnColor){
        return profTable.remove(pawnColor);
    }

    public boolean addStudentInHall(PawnColor pawnColor){
        return hall.add(pawnColor);
    }

    public boolean removeStudentFromHall(PawnColor pawnColor){
        return hall.remove(pawnColor);
    }

    public boolean moveStudentToHall(PawnColor pawnColor){
        if(entrance.remove(pawnColor)){
            hall.add(pawnColor);
            return true;
        }
        return false;
    }

    public boolean removeStudentFromEntrance(PawnColor pawnColor){
        return entrance.remove(pawnColor);
    }

    public boolean addStudentInEntrance(PawnColor pawnColor){
        return entrance.add(pawnColor);
    }

}
