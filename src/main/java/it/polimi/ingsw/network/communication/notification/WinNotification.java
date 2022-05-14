package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

public class WinNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 5495073501053260886L;

    private final String name;
    private final boolean win;

    public WinNotification(String name, boolean win) {
        this.name = name;
        this.win = win;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public boolean isWin() {
        return win;
    }
}
