package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;


public class MoveStudentNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -2243164347217805315L;

    private final int numberOfMovement;
    private final List<PawnColor> movableColor;

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
}
