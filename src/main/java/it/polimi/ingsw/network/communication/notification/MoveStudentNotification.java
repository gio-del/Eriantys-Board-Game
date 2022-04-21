package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;


public class MoveStudentNotification implements Notification {
    @Serial
    private static final long serialVersionUID = -2243164347217805315L;

    private final int numberOfMovement;
    private final List<PawnColor> movableColor;
    private int clientId;

    public MoveStudentNotification(int numberOfMovement, List<PawnColor> movableColor) {
        this.numberOfMovement = numberOfMovement;
        this.movableColor = movableColor;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public int getNumberOfMovement() {
        return numberOfMovement;
    }

    public List<PawnColor> getMovableColor() {
        return movableColor;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
