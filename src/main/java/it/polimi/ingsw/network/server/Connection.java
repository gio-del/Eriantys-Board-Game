package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.communication.notification.Notification;

/**
 * Connection class is a skeleton of what a connection is. It represents a connection between the server and a given client
 */
public interface Connection extends Runnable {

    /**
     * Send a notification to the client
     * @param msg the notification to sent
     */
    void sendMessage(Notification msg);

    /**
     * Close the connection with the client
     */
    void disconnect();
}
