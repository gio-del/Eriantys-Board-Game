package it.polimi.ingsw;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.network.server.Server;

public class EriantysServer {
    public static void main(String[] args) {
        Server server;
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-p") || args[i].equals("--port")) {
                    try {
                        server = new Server(Integer.parseInt(args[i + 1]));
                        server.start();
                        return;
                    } catch (NumberFormatException e) {
                        Server.LOGGER.severe(() -> "Port format not valid, starting server with default port: " + Constants.DEFAULT_PORT);
                        server = new Server(Constants.DEFAULT_PORT);
                        server.start();
                        return;
                    }
                }
            }
        } //if -p was not found
        server = new Server(Constants.DEFAULT_PORT);
        server.start();
    }
}
