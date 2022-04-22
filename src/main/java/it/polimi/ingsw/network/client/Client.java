package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.DisconnectionNotification;
import it.polimi.ingsw.network.communication.notification.Notification;
import it.polimi.ingsw.network.communication.notification.PingNotification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This class represents the client. The client is observed by the ClientController,
 * when a message from server arrives it notifies the ClientController. It is also used
 * by the client controller to send messages to the server.
 */
public class Client extends Thread{
    //TODO: extract interface
    private Socket socket;
    private NotificationVisitor clientSideUpdate; //todo: move this to the client controller
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running = true;

    private final ScheduledExecutorService ping;

    public Client() {
        clientSideUpdate = new ClientSideVisitor();
        ping = new ScheduledThreadPoolExecutor(1);
    }

    public boolean connect(String address,int port){
        try{
            socket = new Socket(address,port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            clientSideUpdate = new ClientSideVisitor();
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
                //msg.accept(clientSideUpdate); todo: to the client controller
            }
        } catch (IOException | ClassNotFoundException e){
            Notification msg = new DisconnectionNotification();
            //msg.accept(clientSideUpdate); todo: to the client controller
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

    /**
     * Send a ping notification to the server each second
     */
    public void runPing(){
        ping.scheduleAtFixedRate(() -> sendMessage(new PingNotification()),0,1, TimeUnit.SECONDS);
    }
}
