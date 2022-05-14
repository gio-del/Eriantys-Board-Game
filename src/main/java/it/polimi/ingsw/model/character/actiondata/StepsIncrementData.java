package it.polimi.ingsw.model.character.actiondata;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.model.character.ActionVisitor;

@JsonTypeName("SET_STEPS_INCREMENT")
public class StepsIncrementData implements ActionData {
    private int increment;

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.visit(this);
    }

    public int getIncrement() {
        return increment;
    }
}
