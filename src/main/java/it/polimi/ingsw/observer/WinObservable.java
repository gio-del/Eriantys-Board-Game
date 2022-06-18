package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class is extended by the entity of the game that represents a possible win condition.
 */
public class WinObservable {
    private final List<WinObserver> observers = new ArrayList<>();

    /**
     * Subscribe a {@link WinObserver} to the observers list.
     *
     * @param observer the observer to subscribe
     */
    public void addObserver(WinObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    public void notifyObserver(Consumer<WinObserver> function) {
        observers.forEach(function);
    }
}
