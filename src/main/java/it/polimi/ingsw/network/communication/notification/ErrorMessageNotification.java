package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * This notification is used by the server to tell all the clients in a specific game that
 * a client disconnected and so the game is terminated
 */
public class ErrorMessageNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 464475177645496215L;
    private final String errorMessage;

    public ErrorMessageNotification(String nickname) {
        this.errorMessage = nickname;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
