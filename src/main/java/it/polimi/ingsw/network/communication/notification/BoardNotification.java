package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.ShortBoard;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * This notification is used by the server to update clients about the board (islands)
 */
public class BoardNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 7688541840773202615L;

    private final ShortBoard board;

    public BoardNotification(ShortBoard board) {
        this.board = board;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public ShortBoard getBoard() {
        return board;
    }
}
