package it.polimi.ingsw.model.character.actiondata;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.model.character.ActionVisitor;

@JsonTypeName("SET_INFLUENCE_STRATEGY")
public class SetInfluenceStrategyData implements ActionData {
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.visit(this);
    }
}
