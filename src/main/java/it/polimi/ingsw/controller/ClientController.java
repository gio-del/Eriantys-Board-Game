package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.client.ClientSideVisitor;
import it.polimi.ingsw.network.communication.NotificationVisitor;

/**
 * A controller on client's side.
 * It observes both {@link it.polimi.ingsw.view.View} and {@link it.polimi.ingsw.network.client.Client}
 * From Server receives message through Client and update the view
 * From View receives input and send them to the server through Client
 * It uses a {@link NotificationVisitor} to dispatch the notification sent by the Server and to perform action on the view
 */
public class ClientController {
    //TODO
    private final NotificationVisitor visitor;

    public ClientController() {
        visitor = new ClientSideVisitor();
    }
}
