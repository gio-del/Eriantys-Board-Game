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

    public boolean addProfessor(PawnColor pawnColor) {
        return profTable.add(pawnColor);
    }

    public boolean removeProfessor(PawnColor pawnColor) {
        return profTable.remove(pawnColor);
    }

    public boolean addStudentInHall(PawnColor pawnColor) {
        return hall.add(pawnColor);
    }

    public boolean addStudentInHall(Pawns pawns) {
        boolean state = false;
        int addedElements=0;
        for(PawnColor pawnColor: PawnColor.values()) {
            int i=0;
            while ((i<pawns.getFromColor(pawnColor)) && addStudentInHall(pawnColor)) {
                i++;
                addedElements++;
            }
        }
        if (addedElements==pawns.totalElements()) state = true;
        return state;
    }

    public boolean removeStudentFromHall(PawnColor pawnColor) {
        return hall.remove(pawnColor);
    }

    public boolean removeStudentFromHall(Pawns pawns) {
        boolean state = false;
        int removedElements=0;
        for(PawnColor pawnColor: PawnColor.values()) {
            int i=0;
            while ((i<pawns.getFromColor(pawnColor)) && removeStudentFromHall(pawnColor)) {
                i++;
                removedElements++;
            }
        }
        if (removedElements==pawns.totalElements()) state = true;
        return state;
    }

    public boolean moveStudentToHall(PawnColor pawnColor) {
        if(entrance.remove(pawnColor)) {
            hall.add(pawnColor);
            return true;
        }
        return false;
    }

    public boolean moveStudentToHall(Pawns pawns) {
        boolean state = false;
        int movedElements=0;
        for(PawnColor pawnColor: PawnColor.values()) {
            int i=0;
            while ((i<pawns.getFromColor(pawnColor)) && removeStudentFromEntrance(pawnColor)) {
                hall.add(pawnColor);
                i++;
                movedElements++;
            }
        }
        if (movedElements==pawns.totalElements()) state = true;
        return state;
    }

    public boolean removeStudentFromEntrance(PawnColor pawnColor) {
        return entrance.remove(pawnColor);
    }

    public boolean removeStudentFromEntrance(Pawns pawns) {
        boolean state = false;
        int removedElements=0;
        for(PawnColor pawnColor: PawnColor.values()) {
            int i=0;
            while ((i<pawns.getFromColor(pawnColor)) && removeStudentFromEntrance(pawnColor)) {
                i++;
                removedElements++;
            }
        }
        if (removedElements==pawns.totalElements()) state = true;
        return state;
    }

    public boolean addStudentInEntrance(PawnColor pawnColor) {
        return entrance.add(pawnColor);
    }

    public boolean addStudentInEntrance(Pawns pawns) {
        boolean state = false;
        int addedElements=0;
        for(PawnColor pawnColor: PawnColor.values()) {
            int i=0;
            while ((i<pawns.getFromColor(pawnColor)) && addStudentInEntrance(pawnColor)) {
                i++;
                addedElements++;
            }
        }
        if (addedElements==pawns.totalElements()) state = true;
        return state;
    }
}
