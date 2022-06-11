package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * With this notification the server tell to the client that the chosen character needs a color to be used, and the client provides it.
 */
public class ColorNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -2636121419519499237L;

    private PawnColor chosen;

    public ColorNotification() {
    }

    public ColorNotification(PawnColor chosen) {
        this.chosen = chosen;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public PawnColor getChosen() {
        return chosen;
    }
}
