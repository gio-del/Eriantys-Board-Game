package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.character.action.ActionType;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Place;

import java.util.List;
import java.util.Objects;

public class CharacterCard implements Place {
    private final String name;
    private final int cost;
    private final String description;
    private final ActionType actionType;
    private final List<String> characterPar;
    private boolean coinOn;

    public CharacterCard(String name, int cost, String description, ActionType actionType, List<String> characterPar) {
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.actionType = actionType;
        this.characterPar = characterPar;
        this.coinOn = false;
    }

    // TODO: CharacterCard can have Pawns on it so it is a place but not all Cards have Pawns...
    @Override
    public boolean remove(Pawns pawns) {
        return false;
    }

    @Override
    public boolean add(Pawns pawns) {
        return false;
    }

    @Override
    public boolean canBeMoved(Pawns pawns) {
        return false;
    }

    @Override
    public boolean canBeRemoved(Pawns pawns) {
        return false;
    }

    public String getName() {
        return name;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getCost() {
        return cost;
    }

    public void setCoinOn() {
        this.coinOn = true;
    }

    public boolean hasCoinOn() {
        return coinOn;
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
