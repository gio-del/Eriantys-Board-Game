package it.polimi.ingsw.network.communication.notification;

import it.polimi.ingsw.network.communication.NotificationVisitor;

import java.io.Serial;
import java.io.Serializable;
//todo: add or remove notifications: mainly add a notification to handle characters use
public abstract class Notification implements Serializable {
    @Serial
    private static final long serialVersionUID = 5085453824300981827L;
    //TODO add: private String genericMessage;
    private String clientID;

    public abstract void accept(NotificationVisitor visitor);
    public void setClientId(String clientId){
        this.clientID = clientId;
    }

    public String getClientID() {
        return clientID;
    }
}
