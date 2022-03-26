package it.polimi.ingsw.model.character;

public class CharacterCard {
    private String name;
    private int cost;
    private String description;
    private ActionType actionType;
    private Action action;

    @Override
    public String toString() {
        return "CharacterCard{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", actionType=" + actionType +
                ", action=" + action +
                '}';
    }
}
