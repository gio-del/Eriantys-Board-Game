package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * A ping message used by the client to communicate that it's still alive
 */
public class PingNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 2766216580858593516L;

    @Override
    public void accept(NotificationVisitor visitor) {
        //ping notification doesn't need to be accepted
    }
}
