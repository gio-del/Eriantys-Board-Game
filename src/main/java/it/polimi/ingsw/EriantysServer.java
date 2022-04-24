package it.polimi.ingsw;

import it.polimi.ingsw.network.server.Server;

import java.util.logging.Level;

public class EriantysServer {
    public static void main(String[] args) {
        //TODO: add args handling
        Server server = new Server(17000);
        Server.logger.setLevel(Level.SEVERE);
        server.start();
    }
}
