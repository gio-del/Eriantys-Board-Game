package it.polimi.ingsw.model.pawns;

import java.util.HashMap;

import static it.polimi.ingsw.model.pawns.PawnColor.*;

public class Pawns {
    public HashMap<PawnColor, Integer> state = new HashMap<>();

    public Pawns() {
        state.put(GREEN,0);
        state.put(RED,0);
        state.put(YELLOW,0);
        state.put(PINK,0);
        state.put(BLUE,0);
    }

    int getFromColor(PawnColor color) {
        return state.get(color);
    }

    void addColor(PawnColor color) {
        state.put(color,state.get(color)+1);
    }

    void removeColor(PawnColor color) {
        state.put(color,state.get(color)-1);
    }

    void addPawns(Pawns nuovi) {

    }

}


