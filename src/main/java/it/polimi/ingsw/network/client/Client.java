package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.DisconnectionNotification;
import it.polimi.ingsw.network.communication.notification.Notification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * This class represents the client. The client is observed by the ClientController,
 * when a message from server arrives it notifies the ClientController. It is used
 * by the client controller to send messages to the server.
 */
public class Client extends Thread{
    //TODO: extract interface
    private Socket socket;
    private NotificationVisitor clientSideUpdate;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running = true;

    public Client() {}

    public boolean connect(String address,int port){
        try{
            socket = new Socket(address,port);
            socket.connect(new InetSocketAddress(address,port), 1000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            clientSideUpdate = new ClientSideVisitor();
            start();
            return true;
        } catch(IOException e){
            return false;
        }
    }

    @Override
    public void run(){
        try {
            while(running){
                Notification msg = (Notification) in.readObject();
                msg.accept(clientSideUpdate);
            }
        } catch (IOException | ClassNotFoundException e){
            Notification msg = new DisconnectionNotification();
            msg.accept(clientSideUpdate);
        }
    }

    public void sendMessage(Notification msg){
        try{
            out.writeObject(msg);
            out.reset();
        } catch (IOException e) {
            System.out.println("Server not reachable");
        }
    }

    //TODO: add a thread that only pings
}
