package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

public class MoveMNNotification implements Notification {
    private final int availableSteps;

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
}
