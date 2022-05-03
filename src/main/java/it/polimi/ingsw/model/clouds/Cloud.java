package it.polimi.ingsw.model.clouds;
import it.polimi.ingsw.model.pawns.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.place.School;

/**
 * This class represents the Clouds from where {@link Player} can choose a {@link Pawns} to move to his {@link School}.
 */
public class Cloud {
    private Pawns students;

    /**
     * Constructs a new Cloud that has a {@link Pawns} of students, every {@link PawnColor} is initialized at {@code 0}.
     */
    public Cloud() {
        this.students = new Pawns();
    }

    /**
     * This method return cloud's students.
     *
     * @return the students in the cloud.
     */
    public Pawns getStudents(){
        return students;
    }

    /**
     * This method return cloud's students and remove then by the cloud.
     *
     * @return the students in the cloud and remove then by the cloud.
     */
    public Pawns getStudentsAndRemove() {
        Pawns extracted = new Pawns();
        extracted.addPawns(this.students);
        this.students = new Pawns();
        return extracted;
    }

    public boolean isEmpty(){
        return students.totalElements()==0;
    }

    /**
     * This method fill the cloud with a {@link Pawns} of students.
     *
     * @param students to move to this cloud
     */
    public void fill(Pawns students) {
        this.students = students;
    }
}