package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.util.List;

public class ChooseCloudNotification implements Notification {
    private final List<Cloud> availableClouds;
    private int clientId;

    public ChooseCloudNotification(List<Cloud> availableClouds) {
        this.availableClouds = availableClouds;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public List<Cloud> getAvailableClouds() {
        return availableClouds;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
