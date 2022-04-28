package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;

public class ChooseWizAndTowerColorNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 7508488393596408467L;

    private List<Wizard> availableWizards;
    private List<TowerColor> availableColors;
    private Wizard wizard;
    private TowerColor towerColor;

    public ChooseWizAndTowerColorNotification(List<Wizard> availableWizards, List<TowerColor> availableColors) {
        this.availableWizards = availableWizards;
        this.availableColors = availableColors;
    }

    public ChooseWizAndTowerColorNotification(Wizard wizard, TowerColor towerColor) {
        this.wizard = wizard;
        this.towerColor = towerColor;
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
