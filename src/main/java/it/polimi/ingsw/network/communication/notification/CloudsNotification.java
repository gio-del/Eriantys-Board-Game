package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;

/**
 * Notification used to update client about the board
 */
public class CloudsNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 3103412624078345365L;

    private final List<ShortCloud> cloudList;

    public CloudsNotification(List<ShortCloud> cloudList) {
        this.cloudList = cloudList;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public List<ShortCloud> getCloudList() {
        return cloudList;
    }
}
