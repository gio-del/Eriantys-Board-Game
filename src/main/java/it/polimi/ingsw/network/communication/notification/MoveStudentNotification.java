package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;


public class MoveStudentNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -2243164347217805315L;

    private List<PawnColor> movableColor;
    private Target target;
    private PawnColor color;

    public MoveStudentNotification(List<PawnColor> movableColor) {
        this.movableColor = movableColor;
    }

    public MoveStudentNotification(PawnColor color, Target target) {
        this.color = color;
        this.target = target;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }


    public List<PawnColor> getMovableColor() {
        return movableColor;
    }

    public Target getTarget() {
        return target;
    }

    public PawnColor getColor() {
        return color;
    }
}
