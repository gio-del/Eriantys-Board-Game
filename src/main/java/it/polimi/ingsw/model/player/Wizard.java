package it.polimi.ingsw.model.player;

/**
 * This enum contains all type of Wizard that can be chosen from {@link Player} at the beginning of the game.
 */
public enum Wizard {
    KING("King"),
    SORCERER("Sorcerer"),
    WITCH("Witch"),
    FLAME_MAGICIAN("Flame magician");

    private final String name;

    Wizard(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
