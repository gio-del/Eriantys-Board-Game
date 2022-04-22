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
    private final int port;
    private final Server server;
    private ServerSocket serverSocket;

    public ServerThread(int port, Server server) {
        this.port = port;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port + ".");
        } catch (IOException e) {
            System.out.println("Server couldn't start");
            return;
        }

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

    public void receiveMessage(Notification msg){
        // TODO: send this to Server object
    }
}
