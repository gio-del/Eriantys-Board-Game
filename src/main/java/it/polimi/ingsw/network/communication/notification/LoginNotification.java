package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

public class LoginNotification implements Notification {
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

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
