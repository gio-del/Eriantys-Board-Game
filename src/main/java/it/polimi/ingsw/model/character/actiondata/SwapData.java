package it.polimi.ingsw.model.character.actiondata;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.model.character.ActionVisitor;

@JsonTypeName("SWAP")
public class SwapData implements ActionData {
    private PlaceData from;
    private PlaceData to;
    private int numbOfMaxSwap;

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

    public int getNumbOfMaxSwap() {
        return numbOfMaxSwap;
    }
}
