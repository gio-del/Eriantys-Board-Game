package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.communication.notification.Notification;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class handle all new clients' connection, foreach clients instantiate a
 * {@link Connection}
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

                Connection connection = new Connection(this,socket);
                new Thread(connection).start();

            } catch (IOException e) {
                System.out.println("Problem during connection with a client");
            }
        }
    }

    public void addClient(String nickname,Connection connection){
        server.addClient(nickname,connection);
    }

    public void receiveMessage(Notification msg){
        server.receiveMessage(msg);
    }
}
