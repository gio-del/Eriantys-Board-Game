package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.network.communication.notification.ErrorMessageNotification;
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
    private final Object outLock = new Object();
    private boolean running = true;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(ClientController clientController) {
        this.clientController = clientController;
        logger.setLevel(Level.SEVERE);
        ping = new ScheduledThreadPoolExecutor(1);
    }

    /**
     * Try to establish a connection with the server
     * @param address the ip of the server
     * @param port the port of the server
     * @return {@code true} if the connection was correctly established, {@code false} otherwise
     */
    public boolean connect(String address, int port) {
        try {
            socket = new Socket(address, port);
            socket.setSoTimeout(6000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            runPing();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * During all the lifecycle of the {@link Client} entity, it is listening to {@link Notification} from the server.
     */
    @Override
    public void run() {
        try {
            while (running) {
                Notification msg = (Notification) in.readObject();
                clientController.receiveMessage(msg);
            }
        } catch (IOException | ClassNotFoundException e) {
            disconnect();
            clientController.onDisconnection();
        }
    }

    /**
     * Send a {@link Notification} to the server
     * @param msg the notification to send
     */
    public void sendMessage(Notification msg) {
        synchronized (outLock) {
            try {
                out.writeObject(msg);
                out.flush();
            } catch (IOException e) {
                logger.severe("Server not reachable!");
                disconnect();
                clientController.receiveMessage(new ErrorMessageNotification("Your connection is off, closing the game.."));
            }
        }
    }

    /**
     * Close the connection with the server in case of error
     */
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                running = false;
                ping.shutdown();
                socket.close();
            }
        } catch (IOException e) {
            logger.severe("Cannot disconnect!");
        }
    }

    /**
     * Send a ping notification to the server each second so that the server knows it's still alive
     */
    public void runPing() {
        ping.scheduleAtFixedRate(() -> sendMessage(new PingNotification()), 0, 1, TimeUnit.SECONDS);
    }
}
