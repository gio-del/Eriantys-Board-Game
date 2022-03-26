package it.polimi.ingsw.model.character;

import java.util.List;
import java.util.Objects;

public class CharacterCard {
    private final String name;
    private final int cost;
    private final String description;
    private final ActionType actionType;
    private final List<String> characterPar;
    //private final Action action;

    public CharacterCard(String name, int cost, String description, ActionType actionType, List<String> characterPar) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterCard that = (CharacterCard) o;
        return cost == that.cost && Objects.equals(name, that.name) && Objects.equals(description, that.description) && actionType == that.actionType && Objects.equals(characterPar, that.characterPar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, description, actionType, characterPar);
    }
}
