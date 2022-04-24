package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

public class NicknameErrorNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 6960993475329284749L;

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }
}
