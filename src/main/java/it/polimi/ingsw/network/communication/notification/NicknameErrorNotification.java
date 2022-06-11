package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * If the chosen nickname by a client is used by another, this notification is sent by the server and the nick should be re-inserted.
 */
public class NicknameErrorNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 6960993475329284749L;

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }
}
