package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

public class GameStartedNotification extends EventNotification {
    @Serial
    private static final long serialVersionUID = -4351151587190714445L;

    public GameStartedNotification() {
        setMessage("Game is started!");
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }
}
