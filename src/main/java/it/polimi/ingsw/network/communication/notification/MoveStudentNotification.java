package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

public class MoveStudentNotification implements Notification {
    private final int numberOfMovement;

    public MoveStudentNotification(int numberOfMovement) {
        this.numberOfMovement = numberOfMovement;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public int getNumberOfMovement() {
        return numberOfMovement;
    }
}
