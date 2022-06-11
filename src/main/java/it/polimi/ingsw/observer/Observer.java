package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.communication.notification.Notification;

/**
 * This interface is implemented by the {@link it.polimi.ingsw.network.server.VirtualView} to use the pattern MVC "locally" in order to hide the network implementation to the model and controller.
 */
public interface Observer {
    /**
     * Receive an update from the observable class.
     * @param msg the notification received
     */
    void update(Notification msg);
}
