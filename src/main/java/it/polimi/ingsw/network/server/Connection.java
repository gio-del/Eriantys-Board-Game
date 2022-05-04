package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.communication.notification.Notification;

public interface Connection extends Runnable {
    void sendMessage(Notification msg);

    void disconnect();
}
