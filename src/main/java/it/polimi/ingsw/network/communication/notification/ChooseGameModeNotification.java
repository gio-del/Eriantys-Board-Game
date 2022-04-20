package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

public class ChooseGameModeNotification implements Notification {
    private final int nPlayers;
    private final boolean isExpertGame;

    public ChooseGameModeNotification(int nPlayers, boolean isExpertGame) {
        this.nPlayers = nPlayers;
        this.isExpertGame = isExpertGame;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public int getNPlayers() {
        return nPlayers;
    }

    public boolean isExpertGame() {
        return isExpertGame;
    }
}
