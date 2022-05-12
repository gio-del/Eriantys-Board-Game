package it.polimi.ingsw.model.character.actiondata;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.model.character.ActionVisitor;

@JsonTypeName("MOVE")
public class MoveData implements ActionData {
    private PlaceData from;
    private PlaceData to;

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.visit(this);
    }

    public PlaceData getFrom() {
        return from;
    }

    public PlaceData getTo() {
        return to;
    }

    public void setFrom(PlaceData from) {
        this.from = from;
    }

    public void setTo(PlaceData to) {
        this.to = to;
    }
}
