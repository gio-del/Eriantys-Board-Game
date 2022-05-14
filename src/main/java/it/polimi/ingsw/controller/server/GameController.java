package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.network.server.*;

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
    private final WinHandler winHandler;

    public GameController() {
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.connectionMap = Collections.synchronizedMap(new HashMap<>());
        this.names = new ArrayList<>();
        this.game = new Game();
        this.turnManager = new TurnManager(game,this);
        this.visitor = new ServerSideVisitor(game,turnManager);
        this.winHandler = new WinHandler(this,game);
    }

    public synchronized void addClient(String nickname, Connection socketConnection) {
        names.add(nickname);
        connectionMap.put(nickname, socketConnection);
        virtualViewMap.put(nickname,new VirtualView(socketConnection));
    }

    /**
     * This method is used to update model according to view's action (client input)
     * @param msg the message sent by the client
     */
    public void handleMessage(Notification msg) {
        msg.accept(visitor);
    }

    public void init(boolean isExpertMode) {
        this.isExpertMode = isExpertMode;
        virtualViewMap.values().forEach(game::addObserver);
        broadcast(new GameStartedNotification());
        turnManager.setFirstOrder(names);
        turnManager.onInit();
    }

    public boolean gameReady() {
        return game.numOfPlayer() == names.size();
    }


    public void handleWin(String name) {
        if(connectionMap.isEmpty()) return; //in case of "multiple win condition" the client are notified one time
        notifyWinner(name);
        connectionMap.clear();
        virtualViewMap.clear();

    }

    private void notifyWinner(String winner){
        virtualViewMap.get(winner).win(winner,true);
        for(String name: names){
            virtualViewMap.get(name).win(winner,false);
        }
    }

    public void handleDisconnection(String nickname) {
        connectionMap.remove(nickname);
        virtualViewMap.remove(nickname);
        Notification disconnection = new DisconnectionNotification(nickname + " has left the match! GAME ENDED.");
        broadcast(disconnection,nickname);
    }

    public void broadcast(Notification msg) {
        msg.setClientId(Server.NAME);
        connectionMap.values().forEach(connection -> connection.sendMessage(msg));
    }

    public void broadcast(Notification msg, String exclusion) {
        connectionMap.keySet().stream().filter(s -> !s.equals(exclusion)).map(connectionMap::get).forEach(connection -> connection.sendMessage(msg));
    }

    public VirtualView getVirtualView(String name){
        return virtualViewMap.get(name);
    }

    protected NotificationVisitor getVisitor() {
        return visitor;
    }

    protected TurnManager getTurnManager() {
        return turnManager;
    }

    protected Game getGame() {
        return game;
    }

    protected WinHandler getWinHandler() {
        return winHandler;
    }

    public boolean isExpertMode() {
        return isExpertMode;
    }

    public void startMatch() {
        game.getBoard().addObserver(winHandler);
        game.getSack().addObserver(winHandler);
    }
}
