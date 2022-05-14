package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.character.ShortCharacter;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.util.List;

public class CharacterNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 7093800236657076836L;

    private int characterId;
    private List<ShortCharacter> characterInUse;

    public CharacterNotification(int characterId) {
        this.characterId = characterId;
    }

    public CharacterNotification(List<ShortCharacter> characterInUse) {
        this.characterInUse = characterInUse;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public int getCharacter() {
        return characterId;
    }

    public List<ShortCharacter> getCharacterInUse() {
        return characterInUse;
    }
}
