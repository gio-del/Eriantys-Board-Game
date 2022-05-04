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

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(3000);

                SocketConnection socketConnection = new SocketConnection(this,socket);
                new Thread(socketConnection).start();

            } catch (IOException e) {
                Server.LOGGER.info(() -> "Problem during connection with a client");
            }
        }
    }

    public void addClient(String nickname, SocketConnection socketConnection){
        server.addClient(nickname, socketConnection);
    }

    public void receiveMessage(Notification msg){
        server.receiveMessage(msg);
    }

    public void handleDisconnection(Socket client) {
        server.handleDisconnection(client);
    }
}
