package it.polimi.ingsw.model.character;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.model.character.actiondata.ActionData;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StudentsOnCharacter.class, name = "studentsOn"),
        @JsonSubTypes.Type(value = CharacterCard.class, name = "standard"),
        @JsonSubTypes.Type(value = BanCharacter.class, name = "banCard")
})
@JsonTypeName("standard")
@JsonRootName(value = "CharacterCard")
public class CharacterCard {
    protected String name;
    protected int cost;
    protected String description;
    protected ActionData action;
    @JsonIgnore
    protected boolean coinOn = false;

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasCoinOn() {
        return coinOn;
    }

    public void setCoinOn(boolean coinOn) {
        this.coinOn = coinOn;
    }

    public String getAction(){
        return action.getClass().getName();
    }
}
