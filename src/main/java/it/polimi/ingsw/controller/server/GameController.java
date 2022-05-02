package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.network.server.Connection;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerSideVisitor;
import it.polimi.ingsw.network.server.VirtualView;

import java.util.*;

/**
 * This class handle a specific game. It observes a VirtualView and update the model {@link Game}
 * It has a reference to each VirtualView with the nickname of the represented client. According to the
 * turn logic (and the state) of the game, it sends messages to the client.
 */
public class GameController {

    private final NotificationVisitor visitor;
    private final Map<String, VirtualView> virtualViewMap;
    private final Map<String, Connection> connectionMap;
    private final List<String> names;
    private final Game game;
    private final TurnManager turnManager;
    private boolean isExpertMode;

    //todo: state pattern

    public GameController() {
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.connectionMap = Collections.synchronizedMap(new HashMap<>());
        this.names = new ArrayList<>();
        this.game = new Game();
        this.turnManager = new TurnManager(game,this);
        this.visitor = new ServerSideVisitor(game,turnManager);
    }

    public synchronized void addClient(String nickname, Connection connection) {
        names.add(nickname);
        connectionMap.put(nickname, connection);
        virtualViewMap.put(nickname,new VirtualView(connection));
    }

    /**
     * This method is used to update model according to view's action (client input)
     * @param msg the message sent by the client
     */
    public void handleMessage(Notification msg) {
        //TODO: check who sent this message, if it's from the current player stop the timer on the connection.
        msg.accept(visitor);
    }

    public void init(boolean isExpertMode) {
        this.isExpertMode = isExpertMode;
        virtualViewMap.values().forEach(game::addObserver);
        EventNotification gameStarted = new GameStartedNotification();
        gameStarted.setMessage("A Match is started!");
        gameStarted.setClientId(Server.NAME);
        broadcast(gameStarted);
        turnManager.setFirstOrder(names);
        turnManager.onInit();
    }

    public boolean gameReady(){
        return game.numOfPlayer() == names.size();
    }


    public void handleDisconnection(String nickname) {
        connectionMap.remove(nickname);
        virtualViewMap.remove(nickname);
        Notification disconnection = new DisconnectionNotification(nickname + " has left the match! GAME ENDED.");
        broadcast(disconnection,nickname);
    }

    public void broadcast(Notification msg) {
        connectionMap.values().forEach(connection -> connection.sendMessage(msg));
    }
    public void broadcast(Notification msg,String exclusion){
        connectionMap.keySet().stream()
                .filter(s -> !s.equals(exclusion))
                .map(connectionMap::get)
                .forEach(connection -> connection.sendMessage(msg));
    }

    public VirtualView getVirtualView(String name){
        return virtualViewMap.get(name);
    }

}
