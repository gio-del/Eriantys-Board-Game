package it.polimi.ingsw.model.pawns;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ShortPawns implements Serializable {
    @Serial
    private static final long serialVersionUID = -371972955700755135L;

    private final Map<PawnColor, Integer> state;

    public ShortPawns(Pawns pawns) {
        state = new EnumMap<>(PawnColor.class);
        for (PawnColor pawnColor : PawnColor.values()) {
            state.put(pawnColor, pawns.getFromColor(pawnColor));
        }
    }

    public int getFromColor(PawnColor pawnColor) {
        return state.get(pawnColor);
    }

    public int totalElements() {
        return state.values().stream().reduce(Integer::sum).orElse(0);
    }

    public List<PawnColor> toList() {
        List<PawnColor> pawnColorList = new ArrayList<>();
        for (PawnColor pawnColor : PawnColor.values())
            IntStream.range(0, state.get(pawnColor)).mapToObj(i -> pawnColor).forEach(pawnColorList::add);
        return pawnColorList;
    }
}
