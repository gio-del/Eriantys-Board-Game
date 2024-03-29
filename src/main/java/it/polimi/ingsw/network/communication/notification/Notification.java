package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Base Notification class. It encourages the use of the Visitor Pattern to dispatch different type of Notification and update server or notifies Views.
 */
public abstract class Notification implements Serializable {
    @Serial
    private static final long serialVersionUID = 5085453824300981827L;

    private String senderID;

    public abstract void accept(NotificationVisitor visitor);

    public void setClientId(String clientId) {
        this.senderID = clientId;
    }

    public String getSenderID() {
        return senderID;
    }
}
