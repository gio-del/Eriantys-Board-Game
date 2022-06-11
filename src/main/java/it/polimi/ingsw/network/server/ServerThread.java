package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.communication.notification.Notification;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class handle all new clients' connection, foreach clients instantiate a
 * {@link SocketConnection}
 */
public class ServerThread implements Runnable {
    private final Server server;
    private final ServerSocket serverSocket;

    public ServerThread(ServerSocket serverSocket, Server server) {
        this.serverSocket = serverSocket;
        this.server = server;
    }

    /**
     * This thread during all his lifetime continuously wait for a client to join. When this happens a connection is established
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(6000);

                SocketConnection socketConnection = new SocketConnection(this, socket);
                new Thread(socketConnection).start();

            } catch (IOException e) {
                Server.LOGGER.info(() -> "Problem during connection with a client");
            }
        }
    }

    /**
     * When a client provide the nickname it is added to the server list of player
     * @param nickname the nickname of the new client
     * @param socketConnection the connection with the client
     */
    public void addClient(String nickname, SocketConnection socketConnection) {
        server.addClient(nickname, socketConnection);
    }

    /**
     * Receive a message from a client. It is passed to the {@link Server}
     * @param msg the notification to receive.
     */
    public void receiveMessage(Notification msg) {
        server.receiveMessage(msg);
    }

    /**
     * Handle a disconnection from a client.
     * @param client the disconnected client socket.
     */
    public void handleDisconnection(Socket client) {
        server.handleDisconnection(client);
    }
}
