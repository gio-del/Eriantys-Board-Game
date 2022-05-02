package it.polimi.ingsw.model;

import it.polimi.ingsw.model.place.ShortIsland;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ShortBoard implements Serializable {
    @Serial
    private static final long serialVersionUID = 4692321416529377896L;

    private final List<ShortIsland> islands;
    private final int motherNaturePos;
    public ShortBoard(Board board) {
        islands = board.getIslands().stream().map(ShortIsland::new).toList();
        this.motherNaturePos = board.getMotherNaturePos();
    }

    public List<ShortIsland> getIslands() {
        return islands;
    }

    public int getMotherNaturePos() {
        return motherNaturePos;
    }
}
