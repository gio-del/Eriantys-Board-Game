package it.polimi.ingsw.model.clouds;

import it.polimi.ingsw.model.pawns.ShortPawns;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a shorted version of a cloud that can be sent to the clients via network
 */
public class ShortCloud implements Serializable {
    @Serial
    private static final long serialVersionUID = -7296350543381614786L;

    private final ShortPawns students;

    public ShortCloud(Cloud cloud) {
        this.students = new ShortPawns(cloud.getStudents());
    }

    public ShortPawns getStudents() {
        return students;
    }

    public boolean isEmpty() {
        return students.totalElements() == 0;
    }
}
