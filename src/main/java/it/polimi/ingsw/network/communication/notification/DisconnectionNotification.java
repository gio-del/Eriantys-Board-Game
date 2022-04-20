package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

public class DisconnectionNotification implements Notification {
    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }
}
