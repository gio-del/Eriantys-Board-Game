package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;

public class ChooseAssistantNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -4567987615843138209L;

    private List<Assistant> playableAssistant;
    private Assistant chosenAssistant = null;

    public ChooseAssistantNotification(List<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
    }

    public ChooseAssistantNotification(Assistant chosenAssistant) {
        this.chosenAssistant = chosenAssistant;
    }

    public void chooseAssistant(Assistant assistant){
        this.chosenAssistant = assistant;
    }
    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public List<Assistant> getPlayableAssistant() {
        return playableAssistant;
    }
}