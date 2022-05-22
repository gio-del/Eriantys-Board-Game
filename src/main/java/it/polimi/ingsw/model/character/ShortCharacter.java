package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.pawns.ShortPawns;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a shorted version of a character that can be sent to the clients via network.
 */
public class ShortCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = -6984393839083135025L;

    private final String name;
    private final String description;
    private final int cost;
    private final ShortPawns studentsOn;
    private final boolean coinOn;
    private final int banTiles;

    public ShortCharacter(CharacterCard character) {
        this.name = character.getName();
        this.description = character.getDescription();
        this.cost = character.getCost();
        this.studentsOn = new ShortPawns(character.getStudents());
        this.coinOn = character.hasCoinOn();
        this.banTiles = character.getNumberOfBanTiles();
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

    public int getBanTiles() {
        return banTiles;
    }
}
