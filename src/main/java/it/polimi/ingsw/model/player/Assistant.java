package it.polimi.ingsw.model.player;

public enum Assistant {
    TURTLE(1,1),
    ELEPHANT(2,1),
    DOG(3,2),
    OCTOPUS(4,2),
    CROCODILE(5,3),
    FOX(6,3),
    EAGLE(7,4),
    CAT(8,4),
    OSTRICH(9,5),
    LION(10,5);

    public final int value,movement;
    Assistant(int value,int movement) {
        this.value = value;
        this.movement = movement;
    }

    public int getValue() {
        return value;
    }

    public int getMovement() {
        return movement;
    }
}
