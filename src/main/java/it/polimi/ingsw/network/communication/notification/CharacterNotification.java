package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

public class CharacterNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 7093800236657076836L;

    private final int characterId;

    public CharacterNotification(int characterId) {
        this.characterId = characterId;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public int getCharacter() {
        return characterId;
    }
}
