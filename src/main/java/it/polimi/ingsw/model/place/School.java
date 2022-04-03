package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;

/**
 * The School of each {@link it.polimi.ingsw.model.player.Player}
 */
public class School {
    private final SchoolPlace entrance;
    private final SchoolPlace hall;
    private final SchoolPlace profTable;

    public School() {
        this.entrance = new Entrance();
        this.hall= new Hall();
        this.profTable = new ProfTable();
    }

    /**
     * Entrance getter
     * @return the entrance as a {@link Pawns}
     */
    public Pawns getEntrance() {
        return entrance.getPawns();
    }

    /**
     * Hall getter
     * @return the hall as a {@link Pawns}
     */
    public Pawns getHall() {
        return hall.getPawns();
    }

    /**
     * ProfessorTable getter
     * @return the Professors Table as a {@link Pawns}
     */
    public Pawns getProfessorTable() {
        return profTable.getPawns();
    }

    public Place getEntranceAsPlace(){
        return entrance;
    }

    public Place getHallAsPlace(){
        return hall;
    }

    /**
     * Assign professor to school
     * @param pawnColor to be assigned
     * @return {@code true} if it was correctly added, otherwise {@code false}
     */
    public boolean addProfessor(PawnColor pawnColor) {
        return profTable.add(new Pawns(pawnColor));
    }

    /**
     * Take professor from school
     * @param pawnColor to be removed
     * @return {@code true} if it was correctly removed, otherwise {@code false}
     */
    public boolean removeProfessor(PawnColor pawnColor) {
        return profTable.remove(new Pawns(pawnColor));
    }

    /**
     * Add student in hall
     * @param pawns represents the student to be added
     * @return {@code true} if it was correctly added, otherwise {@code false}
     */
    public boolean addStudentInHall(Pawns pawns) {
        return hall.add(pawns);
    }

    /**
     * Remove student from hall
     * @param pawns represents the student to be removed
     * @return {@code true} if it was correctly removed, otherwise {@code false}
     */
    public boolean removeStudentFromHall(Pawns pawns) {
        return hall.remove(pawns);
    }

    /**
     * Move student from entrance to hall
     * @param pawns to be moved
     * @return {@code true} if it was correctly moved, otherwise {@code false}
     */
    public boolean moveStudentToHall(Pawns pawns) {
        if(entrance.canBeRemoved(pawns) && hall.canBeMoved(pawns)) {
            entrance.remove(pawns);
            hall.add(pawns);
            return true;
        }
        return false;
    }

    /**
     * Add student in entrance
     * @param pawns to be added
     * @return {@code true} if it was correctly added, otherwise {@code false}
     */
    public boolean addStudentInEntrance(Pawns pawns) {
        return entrance.add(pawns);
    }

    public boolean removeStudentFromEntrance(Pawns pawns) {
        // TODO: is this necessary?
        return entrance.remove(pawns);
    }

}
