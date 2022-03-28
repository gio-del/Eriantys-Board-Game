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

    public boolean addStudent(PawnColor pawnColor) {
        students.addColor(pawnColor);
        return true;
    }

    public boolean addStudent(Pawns pawns) {
        boolean state = false;
        int addedElements=0;
        for(PawnColor pawnColor: PawnColor.values()) {
            int i=0;
            while ((i<pawns.getFromColor(pawnColor)) && addStudent(pawnColor)) {
                i++;
                addedElements++;
            }
        }
        if (addedElements==pawns.totalElements()) state = true;
        return state;
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
