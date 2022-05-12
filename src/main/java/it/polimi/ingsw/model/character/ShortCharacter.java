package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.pawns.ShortPawns;

import java.io.Serial;
import java.io.Serializable;

public class ShortCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = -6984393839083135025L;

    private final String name;
    private final String description;
    private final int cost;
    private final ShortPawns studentsOn;
    private final boolean coinOn;

    public ShortCharacter(CharacterCard character) {
        this.name = character.getName();
        this.description = character.getDescription();
        this.cost = character.getCost();
        this.studentsOn = new ShortPawns(character.getStudents());
        this.coinOn = character.hasCoinOn();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public ShortPawns getStudentsOn() {
        return studentsOn;
    }

    public boolean hasCoinOn() {
        return coinOn;
    }
}
