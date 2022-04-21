package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

public class DisconnectionNotification implements Notification {
    private int clientId;
    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
