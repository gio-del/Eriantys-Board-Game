package it.polimi.ingsw.model.character;

import java.util.List;

public class CharacterCard {
    private final String name;
    private final int cost;
    private final String description;
    private final ActionType actionType;
    private final List<String> characterPar;
    //private final Action action;

    private CharacterCard(String name, int cost, String description, ActionType actionType, List<String> characterPar) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.actionType = actionType;
        this.characterPar = characterPar;
    }

    @Override
    public String toString() {
        return "CharacterCard{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", actionType=" + actionType +
                ", characterPar=" + characterPar +
                '}';
    }
}
