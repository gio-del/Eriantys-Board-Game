package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.utility.gamelimit.GameLimitData;

/**
 * The School of each {@link it.polimi.ingsw.model.player.Player}
 */
public class School {
    private final SchoolPlace entrance;
    private final SchoolPlace hall;
    private final SchoolPlace profTable;
    private final TowerColor towerColor;
    private int towerNum; //TODO: if 4 players only 1 TeamMate have towers -> add TEAM ENUM?

    public School(TowerColor towerColor, GameLimitData gameLimitData, HallManager obs) {
        this.entrance = new Entrance(gameLimitData.getMaxEntrance());
        this.hall = new Hall(obs);
        this.profTable = new ProfTable();
        this.towerColor = towerColor;
        this.towerNum = gameLimitData.getNumberOfTower();
    }

    /**
     * Entrance getter
     *
     * @return the entrance as a {@link Pawns}
     */
    public Pawns getEntrance() {
        return entrance.getPawns();
    }

    /**
     * Hall getter
     *
     * @return the hall as a {@link Pawns}
     */
    public Pawns getHall() {
        return hall.getPawns();
    }

    /**
     * ProfessorTable getter
     *
     * @return the Professors Table as a {@link Pawns}
     */
    public Pawns getProfessorTable() {
        return profTable.getPawns();
    }

    public Place getEntranceAsPlace() {
        return entrance;
    }

    public Place getHallAsPlace() {
        return hall;
    }

    /**
     * Assign professor to school
     *
     * @param pawnColor to be assigned
     * @return {@code true} if it was correctly added, otherwise {@code false}
     */
    public boolean addProfessor(PawnColor pawnColor) {
        return profTable.add(new Pawns(pawnColor));
    }

    /**
     * Take professor from school
     *
     * @param pawnColor to be removed
     * @return {@code true} if it was correctly removed, otherwise {@code false}
     */
    public boolean removeProfessor(PawnColor pawnColor) {
        return profTable.remove(new Pawns(pawnColor));
    }

    /**
     * Add student in hall
     *
     * @param pawns represents the student to be added
     * @return {@code true} if it was correctly added, otherwise {@code false}
     */
    public boolean addStudentInHall(Pawns pawns) {
        return hall.add(pawns);
    }

    /**
     * Remove student from hall
     *
     * @param pawns represents the student to be removed
     * @return {@code true} if it was correctly removed, otherwise {@code false}
     */
    public boolean removeStudentFromHall(Pawns pawns) {
        return hall.remove(pawns);
    }

    /**
     * Move student from entrance to hall
     *
     * @param pawns to be moved
     * @return {@code true} if it was correctly moved, otherwise {@code false}
     */
    public boolean moveStudentToHall(Pawns pawns) {
        if (entrance.canBeRemoved(pawns) && hall.canBeAdded(pawns)) {
            entrance.remove(pawns);
            hall.add(pawns);
            return true;
        }
        return false;
    }

    /**
     * Add student in entrance
     *
     * @param pawns to be added
     * @return {@code true} if it was correctly added, otherwise {@code false}
     */
    public boolean addStudentInEntrance(Pawns pawns) {
        return entrance.add(pawns);
    }

    public boolean removeStudentFromEntrance(Pawns pawns) {
        return entrance.remove(pawns);
    }

    /**
     * @return towerNum present in this {@link School}
     */
    public int getTowerNum() {
        return towerNum;
    }

    /**
     * Add a tower
     */
    public void addTower(int dim) {
        towerNum += dim;
    }

    /**
     * Remove a tower
     *
     * @return color of the tower
     */
    public TowerColor removeTower(int dim) {
        towerNum -= dim;
        return towerColor;
    }

    /**
     * @return towerColor of this {@link School}
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

}
