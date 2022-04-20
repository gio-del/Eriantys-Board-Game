package it.polimi.ingsw;

import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerThread;

public class ServerApp {
    public static void main(String[] args) {
        //TODO: add args handling
        ServerThread serverThread = new ServerThread(17000,new Server());
        serverThread.run();
    }
}
