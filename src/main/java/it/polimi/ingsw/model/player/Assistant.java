package it.polimi.ingsw.model.player;

/**
 * This enum contains all type of Assistant card, every card is unique thanks to the combination of value and movement.
 */
public enum Assistant {
    TURTLE(1, 1),
    ELEPHANT(2, 1),
    DOG(3, 2),
    OCTOPUS(4, 2),
    CROCODILE(5, 3),
    FOX(6, 3),
    EAGLE(7, 4),
    CAT(8, 4),
    OSTRICH(9, 5),
    LION(10, 5);

    private final int value;
    private final int movement;

    /**
     * Constructs a new Assistant Card.
     *
     * @param value    value of the card. The values decide the order of the following turn.
     * @param movement number of steps allowed to Mother Nature.
     */
    Assistant(int value, int movement) {
        this.value = value;
        this.movement = movement;
    }

    /**
     * This method return the value of the card.
     */
    public int value() {
        return value;
    }

    /**
     * This method return the movement of the card.
     */
    public int movement() {
        return movement;
    }
}
