package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.TowerColor;

import java.util.Optional;

public class Island {
    private int dimension;
    private Pawns students;
    private TowerColor tower;

    public Island(){
        this.dimension = 1;
        this.students = new Pawns();
        this.tower = null;
    }

    public void addStudent(PawnColor pawnColor){

    }

    public Optional<TowerColor> conquerIsland(TowerColor towerColor){
        //TODO
        return null;
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
