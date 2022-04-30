package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;

public class ChooseCloudNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 5288677777694463225L;

    private List<ShortCloud> availableClouds;
    private int chosenCloud;

    public ChooseCloudNotification(int chosenCloud) { this.chosenCloud = chosenCloud; }

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

    public int getChosenCloud() {
        return chosenCloud;
    }
}
