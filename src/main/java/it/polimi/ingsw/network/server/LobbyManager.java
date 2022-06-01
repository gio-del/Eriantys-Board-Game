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

    public void onUpdateGameMode(ChooseGameModeNotification chooseGameModeMsg) {
        this.isExpertMode = chooseGameModeMsg.isExpertGame();
        this.nPlayers = chooseGameModeMsg.getNPlayers();
        ready = true;
        checkReadyToStart();
    }

    public synchronized void handleDisconnection(String nickname) {
        if (!players.contains(nickname)) return;
        removePlayerFromLobby(nickname);
        if (firstInLine.equals(nickname) && !players.isEmpty()) { //the first in line was not the only player in the lobby
            vvMap.get(players.peek()).chooseGameMode();
        }
        else if(firstInLine.equals(nickname)) { //the first in line was the only player in the lobby
            ready = false;
        }
        broadcast(nickname + " left the lobby.");
    }

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

    private void broadcast(String message) {
        connectionMap.values().forEach(connection -> connection.sendMessage(new GenericMessageNotification(message)));
    }

    private void broadcast(String message, String exclusion) {
        connectionMap.keySet().stream()
                .filter(name -> !name.equals(exclusion))
                .map(connectionMap::get)
                .forEach(connection -> connection.sendMessage(new GenericMessageNotification(message)));
    }
}
