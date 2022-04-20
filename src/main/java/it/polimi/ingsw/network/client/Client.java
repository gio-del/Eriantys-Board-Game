package it.polimi.ingsw.network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class represents the client. The client is observed by the ClientController,
 * when a message from server arrives it notifies the ClientController. It is used
 * by the client controller to send messages to the server.
 */
public class Client {
    //TODO: extract interface
    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(String address,int port) {
        try{
        socket = new Socket(address,port);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e){
            System.out.println();
        }
    }
    //TODO: there should be a thread that continuously read from the inputStream, receive message and update observer
    //TODO: there should be a method to asynchronously send a message to the server
    //TODO: add a thread that only pings
}
