package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

public class WinNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 5495073501053260886L;

    private final String name;

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
}
