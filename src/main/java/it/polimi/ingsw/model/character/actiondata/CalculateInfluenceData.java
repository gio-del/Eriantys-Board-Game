package it.polimi.ingsw.model.character.actiondata;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.model.character.ActionVisitor;

@JsonTypeName("CALCULATE_INFLUENCE")
public class CalculateInfluenceData implements ActionData {
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.visit(this);
    }
}
