package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * Notification used to let client choose the number of steps that Mother Nature musts perform
 */
public class MoveMNNotification implements Notification {
    @Serial
    private static final long serialVersionUID = -1677653222734549005L;

    private final int availableSteps;
    private int clientId;

    public MoveMNNotification(int availableSteps) {
        this.availableSteps = availableSteps;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public int getAvailableSteps() {
        return availableSteps;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
