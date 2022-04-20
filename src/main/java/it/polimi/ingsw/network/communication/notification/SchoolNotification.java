package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.model.place.School;
import it.polimi.ingsw.network.communication.NotificationVisitor;

public class SchoolNotification implements Notification {
    //TODO: maybe send ReducedSchool, with only schools' representations
    private final School school;

    public SchoolNotification(School school) {
        this.school = school;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public School getSchool() {
        return school;
    }
}
