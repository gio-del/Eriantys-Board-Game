package it.polimi.ingsw.observer;


import it.polimi.ingsw.network.communication.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        if(observer!=null && !observers.contains(observer)){
            observers.add(observer);
        }
    }

    public void notifyObserver(Notification msg){
        observers.forEach(observer -> observer.update(msg));
    }
}
