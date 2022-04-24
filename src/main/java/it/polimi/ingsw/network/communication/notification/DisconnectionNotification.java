package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * This notification is used by the server to tell all the clients in a specific game that
 * a client disconnected and so the game is terminated
 */
public class DisconnectionNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 464475177645496215L;

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }
}
