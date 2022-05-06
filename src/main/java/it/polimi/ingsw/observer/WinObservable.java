package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WinObservable {
    private final List<WinObserver> observers = new ArrayList<>();

    public void addObserver(WinObserver observer){
        if(observer!=null && !observers.contains(observer))
            observers.add(observer);
    }

    public void notifyObserver(Consumer<WinObserver> function){
        observers.forEach(function);
    }
}
