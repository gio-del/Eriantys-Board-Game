package it.polimi.ingsw;

import it.polimi.ingsw.network.client.Client;

public class ClientApp {
    public static void main(String[] args) {
        Client client = new Client();
        client.connect("localhost",17000);
        client.start();
        client.runPing();
    }
}
