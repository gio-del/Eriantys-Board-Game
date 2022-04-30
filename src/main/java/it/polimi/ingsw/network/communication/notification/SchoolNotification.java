package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;

/**
 * This notification is used by the server to show a school to a client
 */
public class SchoolNotification extends Notification {
    @Serial
    private static final long serialVersionUID = -1083964897097839635L;

    private final ShortSchool school;
    private final String owner;

    public SchoolNotification(ShortSchool school, String owner) {
        this.school = school;
        this.owner = owner;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public ShortSchool getSchool() {
        return school;
    }

    public String getOwner() {
        return owner;
    }
}
