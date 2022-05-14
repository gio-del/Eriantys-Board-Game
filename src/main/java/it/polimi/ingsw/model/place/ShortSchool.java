package it.polimi.ingsw.model.place;

import it.polimi.ingsw.model.pawns.ShortPawns;

import java.io.Serial;
import java.io.Serializable;

public class ShortSchool implements Serializable {
    @Serial
    private static final long serialVersionUID = 4591212221569832638L;

    private final ShortPawns entrance;
    private final ShortPawns profTable;
    private final ShortPawns hall;
    private final int numTower;

    public ShortSchool(School school) {
        this.entrance = new ShortPawns(school.getEntrance());
        this.profTable = new ShortPawns(school.getProfessorTable());
        this.hall = new ShortPawns(school.getHall());
        this.numTower = school.getTowerNum();
    }

    public ShortPawns getEntrance() {
        return entrance;
    }

    public ShortPawns getProfTable() {
        return profTable;
    }

    public ShortPawns getHall() {
        return hall;
    }

    public int getNumTower() {
        return numTower;
    }
}
