package it.polimi.ingsw.model.character;

public enum ActionType {
    MOVE_ACTION("MoveAction"),
    SWAP_ACTION("SwapAction"),
    SET_INFLUENCE_STRAT_ACTION("SetInfluenceAction"),
    SET_PROF_STRAT_ACTION("SetProfAction"),
    THIEF_ACTION("ThiefAction"),
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
