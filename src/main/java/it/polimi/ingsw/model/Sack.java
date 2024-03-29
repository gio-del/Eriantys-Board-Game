package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.observer.WinObservable;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * This class represents the Sack from where {@link Pawns} are extracted casually.
 */
public class Sack extends WinObservable {
    private final Pawns sackPawns;
    private final Random random;

    /**
     * Constructs a new Sack that has a {@link Pawns} of students, every {@link PawnColor} is initialized at {@code 0}.
     */
    public Sack() {
        random = new Random();
        this.sackPawns = new Pawns();
    }

    /**
     * This method fill the Sack with {@code 10} pawns of each {@link PawnColor}. Is used for the student's extraction on {@link Island} at the beginning of the game.
     */
    public void initialFill() {
        for (PawnColor pawnColor : PawnColor.values()) {
            sackPawns.addColor(pawnColor, Constants.INIT_SACK_STUDENTS_PER_COLOR);
        }
    }

    /**
     * This method fill the Sack with {@code 22} pawns of each {@link PawnColor}. This fill is meant to be done after
     * the initialFill. These students will be extracted during the game.
     */
    public void fill() {
        for (PawnColor pawnColor : PawnColor.values()) {
            sackPawns.addColor(pawnColor, Constants.STUDENTS_OF_EACH_COLOR - Constants.INIT_SACK_STUDENTS_PER_COLOR);
        }
    }

    /**
     * This method extract casually {@code 1} pawn from Sack.
     *
     * @return {@code 1} pawn.
     */
    public PawnColor extract() {
        PawnColor extracted = sackPawns.getByIndex(random.nextInt(sackPawns.totalElements()));
        sackPawns.removeColor(extracted);
        notifyObserver(obs -> obs.updateSackUsage(sackPawns.totalElements()));
        return extracted;
    }

    /**
     * This method extract casually a group of pawns from Sack.
     *
     * @param size number of pawns to extract.
     * @return a group of pawns.
     */
    public Pawns extractListOfPawns(int size) {
        Pawns extracted = new Pawns();
        IntStream.range(0, size).mapToObj(i -> extract()).forEach(extracted::addColor);
        return extracted;
    }

    /**
     * This method says how many pawns are in the Sack.
     *
     * @return number of pawns in sack.
     */
    public int getNumberOfPawns() {
        return sackPawns.totalElements();
    }

    public void returnTo(Pawns pawns) {
        sackPawns.addPawns(pawns);
    }

    public boolean isEmpty() {
        return sackPawns.totalElements() == 0;
    }

    @Override
    public String toString() {
        return sackPawns.toString();
    }
}