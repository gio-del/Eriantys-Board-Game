package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.util.List;

/**
 * Notification used to update client about the board
 */
public class BoardNotification implements Notification {
    //todo: maybe send ReducedCloud, with only Cloud representation
    private final List<Cloud> cloudList;

    public BoardNotification(List<Cloud> cloudList) {
        this.cloudList = cloudList;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public List<Cloud> getCloudList() {
        return cloudList;
    }
}
