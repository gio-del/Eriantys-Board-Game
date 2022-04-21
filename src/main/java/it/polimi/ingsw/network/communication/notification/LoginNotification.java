package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * Notification used by both client and server to handle login phase
 */
public class LoginNotification implements Notification {
    @Serial
    private static final long serialVersionUID = 360822835800993296L;

    private final String nickname;
    private int clientId;

    public LoginNotification(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public String getNickname() {
        return nickname;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
