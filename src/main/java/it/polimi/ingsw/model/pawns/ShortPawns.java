package it.polimi.ingsw.model.pawns;

import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class ShortPawns implements Serializable {
    @Serial
    private static final long serialVersionUID = -371972955700755135L;

    private final Map<PawnColor,Integer> state;

    public ShortPawns(Pawns pawns) {
        state = new EnumMap<>(PawnColor.class);
        for(PawnColor pawnColor: PawnColor.values()){
            state.put(pawnColor,pawns.getFromColor(pawnColor));
        }
    }

    public int getFromColor(PawnColor pawnColor){
        return state.get(pawnColor);
    }
}
