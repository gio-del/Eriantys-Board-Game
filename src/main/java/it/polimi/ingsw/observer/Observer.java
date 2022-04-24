package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.communication.notification.Notification;

public interface Observer {
    void update(Notification msg);
}
