package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.Set;

/**
 * This notification is used by client and server to communicate during the initial phase of the game
 */
public class ChooseWizAndTowerColorNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 7508488393596408467L;

    private Set<Wizard> availableWizards;
    private Set<TowerColor> availableColors;
    private Wizard wizard;
    private TowerColor towerColor;

    public ChooseWizAndTowerColorNotification(Set<Wizard> availableWizards, Set<TowerColor> availableColors) {
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

    public Set<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    public Set<TowerColor> getAvailableColors() {
        return availableColors;
    }

    public Wizard getWizard() {
        return wizard;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }
}
