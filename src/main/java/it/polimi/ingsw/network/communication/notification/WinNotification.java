package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

public class WinNotification implements Notification {
    private final String name;
    private int clientId;

    public WinNotification(String name) {
        this.name = name;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
