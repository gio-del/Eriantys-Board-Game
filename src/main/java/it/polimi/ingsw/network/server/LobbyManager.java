package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.network.communication.notification.ChooseGameModeNotification;
import it.polimi.ingsw.network.communication.notification.GenericMessageNotification;
import it.polimi.ingsw.view.View;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * This class handle the lobby. When client joins it is queued. If it's the first in line,
 * he is asked to provide number of player and game mode. When there's enough player a match is created.
 */
public class LobbyManager {
    private final Queue<String> players;
    private final Map<String, SocketConnection> connectionMap;
    private final Map<String, View> vvMap;
    private final Server server;
    private boolean ready = false;
    private int nPlayers;
    private boolean isExpertMode;
    private String firstInLine;

    public LobbyManager(Server server) {
        this.server = server;
        this.connectionMap = Collections.synchronizedMap(new HashMap<>());
        this.vvMap = Collections.synchronizedMap(new HashMap<>());
        this.players = new PriorityBlockingQueue<>();
    }

    /**
     * Add a client to the queue in the lobby, and broadcast to the other that this client has been added.
     * If the new client in lobby is the first in line, it's asked about the desired game mode and number of player.
     * When a client is added it is checked if the desires of the first client in line are satisfied.
     *
     * @param nickname         the nickname of the new client in line.
     * @param socketConnection the entity that represents the connection.
     */
    public synchronized void addClient(String nickname, SocketConnection socketConnection) {
        broadcast(nickname + " joined the lobby!", nickname);
        View vv = new VirtualView(socketConnection);
        players.add(nickname);
        connectionMap.put(nickname, socketConnection);
        vvMap.put(nickname, vv);
        if (!ready && players.size() == 1) {
            firstInLine = nickname;
            vv.chooseGameMode();
        }
        checkReadyToStart();
    }

    /**
     * Check if the desires of the first player are satisfied, in this case a game begin and if there's still players in the queue the first is asked the game mode and number of players.
     */
    public void checkReadyToStart() {
        if (ready && players.size() >= nPlayers) {
            startMatch();
            if (!players.isEmpty()) {
                vvMap.get(players.peek()).chooseGameMode();
            }
        }
    }

    public void removePlayerFromLobby(String name) {
        players.remove(name);
        connectionMap.remove(name);
        vvMap.remove(name);
    }

    /**
     * Receive an update with the chosen game mode and number of player, now the game can start if the desires are satisfied.
     *
     * @param chooseGameModeMsg the notification with the chosen game mode and number of player
     */
    public void onUpdateGameMode(ChooseGameModeNotification chooseGameModeMsg) {
        this.isExpertMode = chooseGameModeMsg.isExpertGame();
        this.nPlayers = chooseGameModeMsg.getNPlayers();
        ready = true;
        checkReadyToStart();
    }

    /**
     * Handle the case of a disconnection when a player is in the lobby. The disconnected player is removed from the lobby. If it is
     * the first in line, and it was asked about the game mode, a new player (the new first in line) is asked to provide them.
     *
     * @param nickname the nickname of the disconnected client
     */
    public synchronized void handleDisconnection(String nickname) {
        if (!players.contains(nickname)) return;
        removePlayerFromLobby(nickname);
        if (firstInLine.equals(nickname) && !players.isEmpty()) { //the first in line was not the only player in the lobby
            vvMap.get(players.peek()).chooseGameMode();
        } else if (firstInLine.equals(nickname)) { //the first in line was the only player in the lobby
            ready = false;
        }
        broadcast(nickname + " left the lobby.");
    }

    /**
     * Start a match when the desires of the first player in line are satisfied. A new {@link GameController} is created and the player in game are removed from the lobby.
     * After this the lobby is capable of creating a new match.
     */
    private void startMatch() {
        GameController controller = new GameController();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < nPlayers; i++) {
            String name = players.peek();
            names.add(name);
            controller.addClient(name, connectionMap.get(name));
            removePlayerFromLobby(name);
        }
        controller.init(isExpertMode);
        server.addMatch(names, controller);
        ready = false;
    }

    /**
     * Broadcast a message to all clients in lobby
     *
     * @param message the notification to broadcast
     */
    private void broadcast(String message) {
        connectionMap.values().forEach(connection -> connection.sendMessage(new GenericMessageNotification(message)));
    }

    /**
     * Broadcast a message to all clients in lobby except the exclusion provided.
     *
     * @param message   the notification to broadcast
     * @param exclusion the nick of the client to not sent the notification to
     */
    private void broadcast(String message, String exclusion) {
        connectionMap.keySet().stream()
                .filter(name -> !name.equals(exclusion))
                .map(connectionMap::get)
                .forEach(connection -> connection.sendMessage(new GenericMessageNotification(message)));
    }
}
