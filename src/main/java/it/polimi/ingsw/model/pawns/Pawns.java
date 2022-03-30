package it.polimi.ingsw.model.pawns;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static it.polimi.ingsw.model.pawns.PawnColor.*;

public class Pawns {
    private final Map<PawnColor, Integer> state;

    public Pawns() {
        state = new HashMap<>();
        for(PawnColor pawnColor: PawnColor.values()){
            state.put(pawnColor,0);
        }
    }

    public Pawns(PawnColor pawnColor) {
        this();
        state.put(pawnColor,1);
    }

    public int getFromColor(PawnColor color) {
        return state.get(color);
    }

    public Map<PawnColor, Integer> getMap(Map<PawnColor, Integer> state){
        return state;
    }




    public boolean addColor(PawnColor color) {
        return addColor(color,1);
    }

    public boolean addColor(PawnColor color, int numberOfPawn) {
        state.put(color,state.get(color)+numberOfPawn);
        return true;
    }

    public boolean removeColor(PawnColor color) {
        return removeColor(color,1);
    }

    public boolean removeColor(PawnColor color, int numberOfPawn) {
        if(state.get(color) >= numberOfPawn){
            state.put(color,state.get(color)-numberOfPawn);
            return true;
        }
        return false;
    }

    public boolean addPawns(Pawns pawns) {
        Arrays.stream(values()).forEach(pawnColor -> state.put(pawnColor, state.get(pawnColor) + pawns.getFromColor(pawnColor)));
        return true;
    }

    /**
     *
     * @param pawns to be removed from state
     * @return false if the operation is invalid, true otherwise
     */
    public boolean removePawns(Pawns pawns){
        for(PawnColor pawnColor: PawnColor.values())
            if (getFromColor(pawnColor) < pawns.getFromColor(pawnColor)) return false;
        for(PawnColor pawnColor: PawnColor.values())
            state.put(pawnColor, state.get(pawnColor) - pawns.getFromColor(pawnColor));
        return true;
    }

    public int totalElements(){
        return Arrays.stream(values()).mapToInt(state::get).sum();
    }

    public PawnColor getByIndex(int index) {
        int currentSum = 0;
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

    public void fastSetup(int color1, int color2, int color3, int color4, int color5){
        state.put(PawnColor.GREEN, color1);
        state.put(PawnColor.RED, color2);
        state.put(PawnColor.YELLOW, color3);
        state.put(PawnColor.PINK, color4);
        state.put(PawnColor.BLUE, color5);
    }

    @Override
    public String toString() {
        return state.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawns pawns = (Pawns) o;
        return Objects.equals(state, pawns.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

}


