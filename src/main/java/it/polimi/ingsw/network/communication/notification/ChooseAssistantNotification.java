package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.Set;

/**
 * Both client and server use this notification to communicate during the assistant choosing phase
 */
public class ChooseAssistantNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -4567987615843138209L;

    private Set<Assistant> playableAssistant;
    private Assistant chosenAssistant = null;

    public ChooseAssistantNotification(Set<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
    }

    public ChooseAssistantNotification(Assistant chosenAssistant) {
        this.chosenAssistant = chosenAssistant;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public Set<Assistant> getPlayableAssistant() {
        return playableAssistant;
    }

    public Assistant getChosenAssistant() {
        return chosenAssistant;
    }
}
