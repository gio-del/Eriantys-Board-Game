package it.polimi.ingsw;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.view.cli.Cli;

public class EriantysClient {
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-c") || args[0].equals("--cli")) {
                Cli view = new Cli();
                ClientController clientController = new ClientController(view);
                view.addObserver(clientController);
                view.init();
            }
        }
        else {
            //GUI HERE NOT IMPLEMENTED YET
            Cli view = new Cli();
            ClientController clientController = new ClientController(view);
            view.addObserver(clientController);
            view.init();
        }
    }
}
