package it.polimi.ingsw.model.character.actiondata;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.model.character.ActionVisitor;

@JsonTypeName("THIEF")
public class ThiefData implements ActionData {
    private int removedStudents;

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.visit(this);
    }

    public int getRemovedStudents() {
        return removedStudents;
    }
}
