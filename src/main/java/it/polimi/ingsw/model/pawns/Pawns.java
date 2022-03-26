package it.polimi.ingsw.model.pawns;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.model.pawns.PawnColor.*;

public class Pawns {
    private final Map<PawnColor, Integer> state;

    public Pawns() {
        state = new HashMap<>();
        for(PawnColor pawnColor: PawnColor.values()){
            state.put(pawnColor,0);
        }
    }

    public int getFromColor(PawnColor color) {
        return state.get(color);
    }

    public void addColor(PawnColor color) {
        addColor(color,1);
    }

    public void addColor(PawnColor color, int numberOfPawn) {
        state.put(color,state.get(color)+numberOfPawn);
    }

    public void removeColor(PawnColor color) {
        state.put(color,state.get(color)-1);
    }

    public void addPawns(Pawns pawns) {
        Arrays.stream(values()).forEach(pawnColor -> state.put(pawnColor, state.get(pawnColor) + pawns.getFromColor(pawnColor)));
    }

    public int totalElements(){
        return Arrays.stream(values()).mapToInt(state::get).sum();
    }

    public PawnColor getByIndex(int index) {
        int currentSum = 1;
        PawnColor lastElement = null;
        for (PawnColor pawnColor : state.keySet()){
            if (index < currentSum + state.get(pawnColor)){
                return pawnColor;
            }
            currentSum+= state.get(pawnColor);
            lastElement = pawnColor;
        }
        return lastElement;
    }

    @Override
    public String toString() {
        return state.toString();
    }
}


