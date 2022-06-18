package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.network.communication.notification.ChooseGameModeNotification;
import it.polimi.ingsw.network.communication.notification.NicknameErrorNotification;
import it.polimi.ingsw.network.communication.notification.Notification;
import it.polimi.ingsw.utility.character.CharactersDeck;
import it.polimi.ingsw.utility.gamelimit.GameLimit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.System.exit;

/**
 * This class represents the server.
 */
public class Server {
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static final String NAME = "server";
    /**
     * A relation to know which game a player is part of
     */
    private final Map<String, GameController> matchesMap;
    private final Set<String> alreadyChosenNicknames;
    private final LobbyManager lobbyManager;
    private final Map<Socket, String> socketStringMap;
    private ServerSocket serverSocket;

    public Server(int port) {
        lobbyManager = new LobbyManager(this);
        matchesMap = Collections.synchronizedMap(new HashMap<>());
        socketStringMap = Collections.synchronizedMap(new HashMap<>());
        alreadyChosenNicknames = Collections.synchronizedSet(new HashSet<>());
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.info(() -> "Server started on port " + port + ".");
        } catch (IOException e) {
            LOGGER.severe(() -> "Server couldn't start");
            exit(0);
        }
    }

    /**
     * Boot the server. If any error occur during the booting phase (e.g. config file not found, ecc.) the server is shutdown and a message is shown.
     */
    public void start() {
        checkStart();
        new Thread(new ServerThread(serverSocket, this)).start();
    }

    /**
     * When a client join it is added to the {@link LobbyManager}
     *
     * @param nickname         the nickname of the new client
     * @param socketConnection to be added
     */
    public synchronized void addClient(String nickname, SocketConnection socketConnection) {
        if (alreadyChosenNicknames.contains(nickname)) {
            Notification msg = new NicknameErrorNotification();
            msg.setClientId(Server.NAME);
            socketConnection.sendMessage(msg);
            return;
        }
        LOGGER.info(() -> nickname + " joined the lobby");
        alreadyChosenNicknames.add(nickname);
        socketStringMap.put(socketConnection.getClient(), nickname);
        lobbyManager.addClient(nickname, socketConnection);
    }

    /**
     * When a match start it is added to the map so that when a message from a client arrives the server knows who had to handle that packet.
     *
     * @param names      the list of player in the game
     * @param controller the controller of that game
     */
    public synchronized void addMatch(List<String> names, GameController controller) {
        LOGGER.info(() -> "A match is started.");
        names.forEach(name -> matchesMap.put(name, controller));
    }

    /**
     * Receive a notification from a client. If the sender is in the lobby it is sent to the lobby, otherwise the controller has to handle it
     *
     * @param msg the notification to receive
     */
    public void receiveMessage(Notification msg) {
        if (msg instanceof ChooseGameModeNotification chooseGameModeMsg) {
            lobbyManager.onUpdateGameMode(chooseGameModeMsg);
        } else
            matchesMap.get(msg.getSenderID()).handleMessage(msg);
    }

    /**
     * When a disconnection occurs the server distinguish if the client is in the lobby or in a match. The disconnection is handled in different way.
     * In fact, if the client is in a game, all the players in game are notified and the game ends. Otherwise, if the client is in the lobby the disconnection is handled
     * in a different way.
     *
     * @param client the socket that close the connection
     */
    public synchronized void handleDisconnection(Socket client) {
        String nickname = socketStringMap.get(client);
        if (nickname == null) return; //if the client hasn't chosen nickname he is nor in the lobby nor in a match.
        alreadyChosenNicknames.remove(nickname);
        socketStringMap.remove(client);
        for (Map.Entry<String, GameController> entry : matchesMap.entrySet()) {
            String name = entry.getKey();
            if (name.equals(nickname)) {
                matchesMap.get(name).handleDisconnection(nickname);
                removeMatch(name);
                return;
            }
        }
        lobbyManager.handleDisconnection(nickname);
    }

    /**
     * This method is used to clear a match when a client is disconnected.
     *
     * @param name the name of the disconnected client.
     */
    private void removeMatch(String name) {
        Map<String, GameController> clone = new HashMap<>(matchesMap);
        GameController controller = clone.get(name);
        clone.entrySet().stream().filter(entry -> entry.getValue().equals(controller)).map(Map.Entry::getKey).forEach(matchesMap::remove);
    }

    /**
     * Check if the deck of character and the config file of the matches is read correctly.
     */
    private void checkStart() {
        CharactersDeck.start();
        GameLimit.start();
    }
}
