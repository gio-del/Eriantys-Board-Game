package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * A Notification with a generic message sent by the server. It should contain a generic info about the ongoing game.
 */
public class GenericMessageNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -4559827066327199790L;

    private final String message;

    public GenericMessageNotification(String message) {
        this.message = message;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public String getMessage() {
        return message;
    }
}
