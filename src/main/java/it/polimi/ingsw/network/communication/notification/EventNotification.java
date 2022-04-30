package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

public abstract class EventNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -4351151587190714445L;

    private String message;

    @Override
    public abstract void accept(NotificationVisitor visitor);

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
