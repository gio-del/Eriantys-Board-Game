package it.polimi.ingsw.model.pawns;

import java.util.*;

import static it.polimi.ingsw.model.pawns.PawnColor.*;

/**
 * This class represents students (and professors) that are used in this game. Pawns is a Map between {@link PawnColor} and the number of students (or professor) in every location.
 */
public class Pawns {
    private final Map<PawnColor, Integer> state;

    /**
     * Constructs a new Pawns and initializes every {@link PawnColor} at {@code 0}.
     */
    public Pawns() {
        state = new EnumMap<>(PawnColor.class);
        for(PawnColor pawnColor: PawnColor.values()){
            state.put(pawnColor,0);
        }
    }

    /**
     * Constructs a new Pawns that contains only the specified color
     * @param pawnColor color to be added
     */
    public Pawns(PawnColor pawnColor) {
        this();
        state.put(pawnColor,1);
    }

    /**
     * Constructs a new Pawns and initializes every {@link PawnColor} at the number passed as parameter.
     *
     * @param green the number of green pawns
     * @param red the number of red pawns
     * @param yellow the number of yellow pawns
     * @param pink the number of pink pawns
     * @param blue the number of blue pawns
     */
    public Pawns(int green, int red, int yellow, int pink, int blue){
        this();
        state.put(PawnColor.GREEN, green);
        state.put(PawnColor.RED, red);
        state.put(PawnColor.YELLOW, yellow);
        state.put(PawnColor.PINK, pink);
        state.put(PawnColor.BLUE, blue);
    }

    /**
     * This method return the number of pawns of the color passed as parameter.
     *
     * @param color of pawns looking for.
     * @return number of pawns.
     */
    public int getFromColor(PawnColor color) {
        return state.get(color);
    }

    /**
     * This method add {@code 1} pawn of a {@link PawnColor}.
     *
     * @param color to be added.
     * @return true if the operation work correctly, false otherwise.
     */
    public boolean addColor(PawnColor color) {
        return addColor(color,1);
    }

    /**
     * This method add a group of pawns of a {@link PawnColor} to Pawns.
     *
     * @param color of the pawns.
     * @param numberOfPawn number of pawns of color to be added.
     * @return true if the operation work correctly, false otherwise.
     */
    public boolean addColor(PawnColor color, int numberOfPawn) {
        state.put(color,state.get(color)+numberOfPawn);
        return true;
    }

    /**
     * This method remove {@code 1} pawn of a {@link PawnColor}.
     *
     * @param color to be removed.
     * @return true if the operation work correctly, false otherwise.
     */
    public boolean removeColor(PawnColor color) {
        return removeColor(color,1);
    }

    /**
     * This method remove a group of pawns of a {@link PawnColor} to Pawns.
     *
     * @param color of the pawns.
     * @param numberOfPawn number of pawns of color to be removed.
     * @return true if the operation work correctly, false otherwise.
     */
    public boolean removeColor(PawnColor color, int numberOfPawn) {
        if(state.get(color) >= numberOfPawn){
            state.put(color,state.get(color)-numberOfPawn);
            return true;
        }
        return false;
    }

    /**
     * This method add a group of pawns to Pawns.
     *
     * @param pawns to be added.
     * @return true if the operation work correctly, false otherwise.
     */
    public boolean addPawns(Pawns pawns) {
        Arrays.stream(values()).forEach(pawnColor -> state.put(pawnColor, state.get(pawnColor) + pawns.getFromColor(pawnColor)));
        return true;
    }

    /**
     * This method remove a group of pawns from Pawns if they can be removed.
     *
     * @param pawns to be removed from state.
     * @return false if the operation is invalid, true otherwise.
     */
    public boolean removePawns(Pawns pawns){
        if(canBeRemoved(pawns)) {
            Arrays.stream(values()).forEach(pawnColor -> state.put(pawnColor, state.get(pawnColor) - pawns.getFromColor(pawnColor)));
            return true;
        }
        return false;
    }

    /**
     * This method says if a group of pawns can be removed from Pawns.
     *
     * @param pawns to check.
     * @return false if the operation is invalid, true otherwise.
     */
    public boolean canBeRemoved(Pawns pawns){
        for(PawnColor pawnColor: PawnColor.values())
            if(getFromColor(pawnColor) < pawns.getFromColor(pawnColor)) return false;
        return true;
    }

    /**
     * This method return the number of elements presents in a pawn (no distinction of {@link PawnColor}).
     *
     * @return number of total elements.
     */
    public int totalElements() {
        return Arrays.stream(values()).mapToInt(state::get).sum();
    }

    public PawnColor getByIndex(int index) {
        int currentSum = 0;
        PawnColor lastElement = null;
        for(Map.Entry<PawnColor,Integer> entry: state.entrySet()){
            PawnColor pawnColor = entry.getKey();
            if(index < currentSum + state.get(pawnColor)){
                return pawnColor;
            }
            currentSum += state.get(pawnColor);
            lastElement = pawnColor;
        }
        return lastElement;
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


