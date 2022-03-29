package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.TowerColor;

import java.util.Optional;

public class Island {
    private final int dimension;
    private final Pawns students;
    private final TowerColor tower;

    public Island(){
        this.dimension = 1;
        this.students = new Pawns();
        this.tower = null;
    }

    public boolean addStudent(PawnColor pawnColor) {
        return students.addColor(pawnColor);
    }

    public boolean addStudent(Pawns pawns) {
        students.addPawns(pawns);
        return true;
    }

    public Optional<TowerColor> conquerIsland(TowerColor towerColor){
        //TODO
        return Optional.empty();
    }

    public int getDimension() {
        return dimension;
    }

    public Pawns getStudents() {
        return students;
    }

    public Optional<TowerColor> getTower() {
        return Optional.ofNullable(tower);
    }
}
