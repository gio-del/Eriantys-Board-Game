package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The view extends this interface.
 */
public class ClientObservable {
    private final List<ClientObserver> observers = new ArrayList<>();

    public void addObserver(ClientObserver observer){
        if(observer!=null && !observers.contains(observer))
            observers.add(observer);
    }

    public void notifyObserver(Consumer<ClientObserver> function){
        observers.forEach(function);
    }
}
