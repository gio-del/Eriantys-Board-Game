package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.ErrorMessageNotification;
import it.polimi.ingsw.network.communication.notification.GenericMessageNotification;
import it.polimi.ingsw.network.communication.notification.ModelUpdateNotification;
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
    private final NotificationVisitor visitor;
    private final Map<String, VirtualView> virtualViewMap;
    private final Map<String, Connection> connectionMap;
    private final List<String> names;
    private final Game game;
    private final TurnManager turnManager;
    private final WinHandler winHandler;
    private boolean expertMode;

    public GameController() {
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.connectionMap = Collections.synchronizedMap(new HashMap<>());
        this.names = Collections.synchronizedList(new ArrayList<>());
        this.game = new Game();
        this.turnManager = new TurnManager(game, this);
        this.visitor = new ServerSideVisitor(game, turnManager);
        this.winHandler = new WinHandler(this, game);
    }

    /**
     * Add a client in the game
     *
     * @param nickname         the nickname of the player
     * @param socketConnection the connection entity with the player
     */
    public synchronized void addClient(String nickname, Connection socketConnection) {
        names.add(nickname);
        connectionMap.put(nickname, socketConnection);
        virtualViewMap.put(nickname, new VirtualView(socketConnection));
    }

    /**
     * This method is used to update model according to view's action (client input)
     *
     * @param msg the message sent by the client
     */
    public void handleMessage(Notification msg) {
        msg.accept(visitor);
    }

    public void init(boolean expertMode) {
        this.expertMode = expertMode;
        virtualViewMap.values().forEach(game::addObserver);
        broadcast(new GenericMessageNotification("Game is started!"));
        turnManager.setFirstOrder(names);
        turnManager.onInit();
    }

    /**
     * The game is ready when all the players have chosen the Wizard and Tower Color.
     *
     * @return true if the game is ready to start, false otherwise.
     */
    public boolean gameReady() {
        return game.numOfPlayer() == names.size();
    }

    /**
     * When the Winner Handler notifies the controller, this method is used to show all client the winner and close the game.
     *
     * @param name the winner nickname
     */
    public void handleWin(String name) {
        if (connectionMap.isEmpty())
            return; //in case of "multiple win condition" (e.g. a player finishes all his towers and the island are less than 3) the client are notified one time
        game.notifyObserver(new ModelUpdateNotification(new ShortModel(game, expertMode))); //final update
        notifyWinner(name);
    }

    /**
     * Show win message via the virtual views
     *
     * @param winner the nickname of the winner
     */
    private synchronized void notifyWinner(String winner) {
        virtualViewMap.get(winner).win(winner, true);
        removeClient(winner);
        names.remove(winner);
        for (String name : names) {
            virtualViewMap.get(name).win(winner, false);
            removeClient(name);
        }
        names.clear();
    }

    private void removeClient(String nickname) {
        connectionMap.remove(nickname);
        virtualViewMap.remove(nickname);
    }

    /**
     * Broadcast a disconnection message to all the listening client and close the game
     *
     * @param nickname the nickname of the disconnected client
     */
    public synchronized void handleDisconnection(String nickname) {
        connectionMap.remove(nickname);
        virtualViewMap.remove(nickname);
        Notification disconnection = new ErrorMessageNotification(nickname + " has left the match! GAME ENDED.");
        broadcast(disconnection, nickname);
    }

    /**
     * Broadcast a message to all the connected client
     *
     * @param msg the message to broadcast
     */
    public void broadcast(Notification msg) {
        msg.setClientId(Server.NAME);
        connectionMap.values().forEach(connection -> connection.sendMessage(msg));
    }

    /**
     * Broadcast a message to all the client, except the specified one
     *
     * @param msg       the message to broadcast
     * @param exclusion the excluded client
     */
    public void broadcast(Notification msg, String exclusion) {
        connectionMap.keySet().stream().filter(s -> !s.equals(exclusion)).map(connectionMap::get).forEach(connection -> connection.sendMessage(msg));
    }

    /**
     * Virtual View getter
     *
     * @param name the nickname of the client
     * @return the virtual view associated to that nickname
     */
    public VirtualView getVirtualView(String name) {
        return virtualViewMap.get(name);
    }

    /**
     * Notification Visitor getter
     *
     * @return the server side visitor
     */
    protected NotificationVisitor getVisitor() {
        return visitor;
    }

    /**
     * Turn Manager getter
     *
     * @return the turn manager of this game
     */
    protected TurnManager getTurnManager() {
        return turnManager;
    }

    /**
     * Game getter
     *
     * @return the model representation of this match
     */
    protected Game getGame() {
        return game;
    }

    /**
     * WinHandler getter
     * The Winner Handler of this game
     *
     * @return the Winner Handler of this game
     */
    protected WinHandler getWinHandler() {
        return winHandler;
    }

    /**
     * @return true if the game mode is set, false otherwise.
     */
    public boolean isExpertMode() {
        return expertMode;
    }

    /**
     * In order to start the match the board and the sack are subscribed to the WinHandler
     */
    public void startMatch() {
        game.getBoard().addObserver(winHandler);
        game.getSack().addObserver(winHandler);
    }
}
