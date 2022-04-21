package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

public class WinNotification implements Notification {
    @Serial
    private static final long serialVersionUID = 5495073501053260886L;

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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
