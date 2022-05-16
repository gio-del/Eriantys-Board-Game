package it.polimi.ingsw.model.character.actiondata;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.model.character.ActionVisitor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "actionType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MoveData.class, name = "MOVE"),
        @JsonSubTypes.Type(value = SetProfStrategyData.class, name = "SET_PROF_STRATEGY"),
        @JsonSubTypes.Type(value = CalculateInfluenceData.class, name = "CALCULATE_INFLUENCE"),
        @JsonSubTypes.Type(value = SetInfluenceStrategyData.class, name = "SET_INFLUENCE_STRATEGY"),
        @JsonSubTypes.Type(value = SwapData.class, name = "SWAP"),
        @JsonSubTypes.Type(value = ThiefData.class, name = "THIEF"),
        @JsonSubTypes.Type(value = StepsIncrementData.class, name = "SET_STEPS_INCREMENT"),
        @JsonSubTypes.Type(value = BanData.class, name = "BAN")
})
@JsonTypeName("action")
public interface ActionData {
    void accept(ActionVisitor visitor);
}
