package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serializable;

public interface Notification extends Serializable {
    //todo: add or remove other notification
    void accept(NotificationVisitor visitor);
    void setClientId(int clientId);
}
