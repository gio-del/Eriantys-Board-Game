package it.polimi.ingsw.observer;


import it.polimi.ingsw.network.communication.notification.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is extended by the model to use the MVC pattern.
 */
public abstract class Observable {
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Subscribe an {@link Observer} to the list of the observers.
     *
     * @param observer the observer to subscribe
     */
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Notify the observers.
     *
     * @param msg the notification
     */
    public void notifyObserver(Notification msg) {
        observers.forEach(observer -> observer.update(msg));
    }
}
