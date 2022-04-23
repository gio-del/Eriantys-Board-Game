package it.polimi.ingsw.model.character.action;

public enum ActionType {
    MOVE("Move"),
    SWAP("Swap"),
    SET_INFLUENCE_STRATEGY("SetInfluenceAction"),
    SET_PROF_STRATEGY("SetProfAction"),
    THIEF("ThiefAction"),
    CALCULATE_INFLUENCE("CalculateInfluence");

    private final String text;

    ActionType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }
}
