package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.DisconnectionNotification;
import it.polimi.ingsw.network.communication.notification.EventNotification;
import it.polimi.ingsw.network.communication.notification.GameStartedNotification;
import it.polimi.ingsw.network.communication.notification.Notification;
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

    private NotificationVisitor visitor;
    private final Map<String, VirtualView> virtualViewMap;
    private final Map<String, Connection> connectionMap;
    private Game game;

    //todo: state pattern
    public enum GameState{PLANNING_ADD_TO_CLOUD,PLANNING_ASSISTANT,ACTION_MOVE,ACTION_MN,ACTION_CHOOSE_CLOUD,WAIT}
    private GameState gameState;

    public GameController() {
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.connectionMap = Collections.synchronizedMap(new HashMap<>());
    }

    public synchronized void addClient(String nickname, Connection connection) {
        connectionMap.put(nickname, connection);
        VirtualView vv = new VirtualView(connection);
        virtualViewMap.put(nickname,vv);
    }

    /**
     * This method is used to update model according to view's action (client input)
     * @param msg the message sent by the client
     */
    public void handleMessage(Notification msg) {
        //TODO: check who sent this message, if it's from the current player stop the timer on the connection.
        msg.accept(visitor);
    }

    public void startGame(int nPlayers, boolean isExpertMode) {
        game = new Game(nPlayers, isExpertMode);
        this.visitor = new ServerSideVisitor(game,this);
        for(VirtualView vv: virtualViewMap.values())
            game.addObserver(vv);
        EventNotification gameStarted = new GameStartedNotification();
        gameStarted.setMessage("A match is started!");
        gameStarted.setClientId(Server.NAME);
        broadcast(gameStarted);
        game.startGame();
    }



    public void handleDisconnection(String nickname) {
        Notification disconnection = new DisconnectionNotification(nickname);
        broadcast(disconnection,nickname);
    }

    public void broadcast(Notification msg){
        connectionMap.values().forEach(connection -> connection.sendMessage(msg));
    }
    public void broadcast(Notification msg,String exclusion){
        connectionMap.keySet().stream()
                .filter(s -> !s.equals(exclusion))
                .map(connectionMap::get)
                .forEach(connection -> connection.sendMessage(msg));
    }

    public void setState(GameState state){
        this.gameState = state;
    }

}
