package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.ShortPawns;
import it.polimi.ingsw.model.player.TowerColor;

import java.io.Serial;
import java.io.Serializable;

public class ShortIsland implements Serializable {
    @Serial
    private static final long serialVersionUID = -699231671818788011L;

    private final ShortPawns students;
    private final TowerColor tower;
    private final int dimension;

    public ShortIsland(Island island) {
        students = new ShortPawns(island.getStudents());
        tower = island.getTower();
        dimension = island.getDimension();
    }

    public ShortPawns getStudents() {
        return students;
    }

    public TowerColor getTower() {
        return tower;
    }

    public int getDimension() {
        return dimension;
    }
}
