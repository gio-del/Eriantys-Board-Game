package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

public interface Notification {
    //todo: add or remove other notification
    void accept(NotificationVisitor visitor);
}
