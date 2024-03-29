package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * Notification used to let client choose the number of steps that Mother Nature musts perform
 */
public class MoveMNNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -1677653222734549005L;

    private final int steps;

    public MoveMNNotification(int steps) {
        this.steps = steps;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public int getSteps() {
        return steps;
    }
}
