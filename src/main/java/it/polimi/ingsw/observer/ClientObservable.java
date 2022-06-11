package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The views (GUI,CLI and not the virtual one) extends this interface to communicate with the client controller.
 */
public class ClientObservable {
    protected final List<ClientObserver> observers = new ArrayList<>();

    /**
     * Subscribe a {@link ClientObserver} to the list of the observers
     * @param observer the observer to subscribe
     */
    public void addObserver(ClientObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    /**
     * This method is used to call a function of the observer
     * @param function the function to call
     */
    public void notifyObserver(Consumer<ClientObserver> function) {
        observers.forEach(function);
    }
}
