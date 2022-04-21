package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.ShortCloud;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;

public class ChooseCloudNotification implements Notification {
    @Serial
    private static final long serialVersionUID = 5288677777694463225L;

    private int clientId;
    private final List<ShortCloud> availableClouds;

    public ChooseCloudNotification(List<ShortCloud> availableClouds) {
        this.availableClouds = availableClouds;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public List<ShortCloud> getAvailableClouds() {
        return availableClouds;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
