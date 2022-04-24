package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;

public class ChooseWizAndTowerColorNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 7508488393596408467L;

    private final List<Wizard> availableWizards;
    private final List<TowerColor> availableColors;

    public ChooseWizAndTowerColorNotification(List<Wizard> availableWizards, List<TowerColor> availableColors) {
        this.availableWizards = availableWizards;
        this.availableColors = availableColors;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    public List<TowerColor> getAvailableColors() {
        return availableColors;
    }
}
