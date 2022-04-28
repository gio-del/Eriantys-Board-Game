package it.polimi.ingsw;

import it.polimi.ingsw.network.server.Server;

public class EriantysServer {
    public static void main(String[] args) {
        //TODO: add args handling
        Server server = new Server(17000);
        server.start();
    }
}
