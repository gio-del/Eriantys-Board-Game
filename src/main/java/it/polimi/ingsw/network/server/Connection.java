package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.Notification;

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
    private NotificationVisitor serverSideVisitor;
    private int clientId;

    public Connection(ServerThread server, Socket client) {
        this.server = server;
        this.client = client;
        this.running = true;

        try {
            this.out = new ObjectOutputStream(client.getOutputStream());
            this.in = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("Cannot instantiate a connection with client");
            this.running = false;
        }
    }

    @Override
    public void run() {
        System.out.println("Client connected from" + client.getInetAddress());
        try{
            while(!Thread.currentThread().isInterrupted()){
                Notification notification = (Notification) in.readObject();
                notification.setClientId(clientId);
                notification.accept(serverSideVisitor);
            }
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Invalid notification received from client.");
        } finally {
            disconnect();
        }
    }

    public void sendMessage(Notification msg){
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            System.out.println("Couldn't send message to a client, closing connection");
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
                    System.out.println("Cannot close connection with client");
                }
            }
        }
        //TODO: add a call to a method of ServerThread that handle disconnection
    }
}
