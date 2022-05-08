package it.polimi.ingsw.model.character;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Place;


@JsonTypeName("studentsOn")
public class StudentsOnCharacter extends CharacterCard implements Place {
    @JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="numberOfStudentsOn")
    private int numberOfStudentsOn;
    private Pawns students;

    @Override
    public boolean remove(Pawns pawns) {
        if(canBeRemoved(pawns))
            return students.removePawns(pawns);
        return false;
    }

    @Override
    public boolean add(Pawns pawns) {
        if(canBeRemoved(pawns))
            return students.addPawns(pawns);
        return false;
    }

    @Override
    public boolean canBeMoved(Pawns pawns) {
        return pawns.totalElements() + students.totalElements() <= numberOfStudentsOn;
    }

    @Override
    public boolean canBeRemoved(Pawns pawns) {
        return students.canBeRemoved(pawns);
    }
}
