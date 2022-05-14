package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.network.communication.notification.Notification;
import it.polimi.ingsw.network.communication.notification.PingNotification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the client. The client is observed by the ClientController,
 * when a message from server arrives it notifies the ClientController. It is also used
 * by the client controller to send messages to the server.
 */
public class Client extends Thread {
    private static final Logger logger = Logger.getLogger(Client.class.getSimpleName());
    private final ClientController clientController;
    private final ScheduledExecutorService ping;
    private final boolean running = true;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(ClientController clientController) {
        this.clientController = clientController;
        logger.setLevel(Level.SEVERE);
        ping = new ScheduledThreadPoolExecutor(1);
    }

    public boolean connect(String address, int port) {
        try {
            socket = new Socket(address, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            runPing();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                Notification msg = (Notification) in.readObject();
                clientController.receiveMessage(msg);
            }
        } catch (IOException | ClassNotFoundException e) {
            clientController.onDisconnection();
        }
    }

    public void sendMessage(Notification msg) {
        try {
            out.writeObject(msg);
            out.reset();
        } catch (IOException e) {
            logger.info("Server not reachable!");
            //todo: what happens then?
        }
    }

    /**
     * Send a ping notification to the server each second so that the server knows it's still alive
     */
    public void runPing() {
        ping.scheduleAtFixedRate(() -> sendMessage(new PingNotification()), 0, 1, TimeUnit.SECONDS);
    }
}
