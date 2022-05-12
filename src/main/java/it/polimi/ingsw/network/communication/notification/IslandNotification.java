package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

public class IslandNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -5782613848682880212L;

    private int island;

    public IslandNotification() {
    }

    public IslandNotification(int island) {
        this.island = island;
    }

    public int getIsland() {
        return island;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }
}
