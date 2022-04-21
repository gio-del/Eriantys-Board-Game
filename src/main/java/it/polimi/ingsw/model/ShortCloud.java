package it.polimi.ingsw.model;

import it.polimi.ingsw.model.pawns.ShortPawns;

import java.io.Serial;
import java.io.Serializable;

public class ShortCloud implements Serializable {
    @Serial
    private static final long serialVersionUID = -7296350543381614786L;

    ShortPawns students;

    public ShortCloud(Cloud cloud) {
        this.students = new ShortPawns(cloud.getStudents());
    }

    public ShortPawns getStudents() {
        return students;
    }
}
