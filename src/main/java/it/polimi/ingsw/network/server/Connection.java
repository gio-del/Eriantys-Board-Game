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
public class Connection implements Runnable {
    private final ServerThread server;
    private final Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running;

    public Connection(ServerThread server, Socket client) {
        this.server = server;
        this.client = client;
        this.running = true;
        //TODO: add a timer to each request: there must be also a stopTimer() method to be called each time a response arrives.
        try {
            this.out = new ObjectOutputStream(client.getOutputStream());
            this.in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            Server.logger.info("Cannot instantiate a connection with client");
            this.running = false;
        }
    }

    @Override
    public void run() {
        String msg = "Client connected from " + client.getInetAddress();
        Server.logger.info(msg);
        try{
            while(!Thread.currentThread().isInterrupted()){
                Notification notification = (Notification) in.readObject();
                if(notification instanceof LoginNotification loginNotification){
                    server.addClient(loginNotification.getNickname(),this);
                }
                else if(!(notification instanceof PingNotification))
                    server.receiveMessage(notification);
            }
        } catch (IOException | ClassNotFoundException e){
            // this means that the client is offline
            Server.logger.info("Client is offline.");
        } finally {
            disconnect();
        }
    }

    /**
     * Sends a message to the client
     * @param msg to be sent
     */
    public void sendMessage(Notification msg){
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            Server.logger.info("Couldn't send message to a client, closing connection");
            disconnect();
        }
    }

    private void disconnect(){
        if(running){
            running=false;
            if(!client.isClosed()){
                try {
                    client.close();
                } catch (IOException e) {
                    Server.logger.info("Cannot close connection with client");
                }
            }
        }
        //TODO: add a call to a method of ServerThread that handle disconnection, e.g. say
        //todo: to the server that that connection is off, server takes the GameController and end that match
    }
}
