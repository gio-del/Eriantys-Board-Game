package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.communication.notification.LoginNotification;
import it.polimi.ingsw.network.communication.notification.Notification;
import it.polimi.ingsw.network.communication.notification.PingNotification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class represents a connection between the server and a client.
 * Server uses this class to communicate with the client. When a {@link Notification} arrives it is passed to server.
 */
public class SocketConnection implements Connection {
    private final ServerThread server;
    private final Socket client;
    private final Object outLock = new Object();
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running;

    public SocketConnection(ServerThread server, Socket client) {
        this.server = server;
        this.client = client;
        this.running = true;
        try {
            this.out = new ObjectOutputStream(client.getOutputStream());
            this.in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            Server.LOGGER.info("Cannot instantiate a connection with client");
            this.running = false;
        }
    }

    /**
     * During the lifetime of the connection it waits for the client to send a message. When this happens the message is handled.
     * If it is a login notification (nickname provided by a client) the client is added to the server list of clients.
     * If it is a {@link PingNotification} a {@link PingNotification} in response is sent.
     * Otherwise, the server has to handle it.
     */
    @Override
    public void run() {
        Server.LOGGER.info(() -> "Client connected from " + client.getInetAddress());
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Notification notification = (Notification) in.readObject();
                if (notification instanceof LoginNotification loginNotification) {
                    server.addClient(loginNotification.getNickname(), this);
                } else if ((notification instanceof PingNotification)) {
                    sendMessage(new PingNotification()); //pong
                } else {
                    Server.LOGGER.info(() -> "Message received from: " + notification.getSenderID() + ". Type: " + notification.getClass().getName());
                    server.receiveMessage(notification);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Server.LOGGER.info(() -> "Client is offline.");
        } finally {
            disconnect();
        }
    }

    /**
     * Sends a message to the client
     *
     * @param msg to be sent
     */
    @Override
    public void sendMessage(Notification msg) {
        synchronized (outLock) {
            try {
                out.writeObject(msg);
            } catch (IOException e) {
                Server.LOGGER.info(() -> "Couldn't send message to a client, closing connection.");
                disconnect();
            }
        }
    }

    /**
     * Close the connection with the client
     */
    @Override
    public void disconnect() {
        if (running) {
            running = false;
            if (!client.isClosed()) {
                try {
                    client.close();
                } catch (IOException e) {
                    Server.LOGGER.info(() -> "Cannot close connection with client");
                }
            }
        }
        server.handleDisconnection(this.client);
    }

    protected Socket getClient() {
        return client;
    }
}
