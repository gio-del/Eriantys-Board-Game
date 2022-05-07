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
    /**
     * A relation to know which game a player is part of
     */
    private final Map<String, GameController> matchesMap;
    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static final String NAME = "server";
    private final Set<String> alreadyChosenNicknames;
    private ServerSocket serverSocket;
    private final LobbyManager lobbyManager;
    private final Map<Socket,String> socketStringMap;

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

    public void start() {
        checkStart();
        new Thread(new ServerThread(serverSocket,this)).start();
    }

    /**
     * When a client join it is added to the {@link LobbyManager}
     * @param socketConnection to be added
     */
    public synchronized void addClient(String nickname, SocketConnection socketConnection) {
        if(alreadyChosenNicknames.contains(nickname)) {
            Notification msg = new NicknameErrorNotification();
            msg.setClientId(Server.NAME);
            socketConnection.sendMessage(msg);
            return;
        }
        LOGGER.info(() -> nickname + " joined the lobby");
        alreadyChosenNicknames.add(nickname);
        socketStringMap.put(socketConnection.getClient(),nickname);
        lobbyManager.addClient(nickname, socketConnection);
    }

    public synchronized void addMatch(List<String> names,GameController controller) {
        LOGGER.info(() -> "A match is started.");
        names.forEach(name -> matchesMap.put(name,controller));
    }

    public void receiveMessage(Notification msg) {
        if(msg instanceof ChooseGameModeNotification chooseGameModeMsg) {
            lobbyManager.onUpdateGameMode(chooseGameModeMsg);
        }
        else
            matchesMap.get(msg.getSenderID()).handleMessage(msg);
    }

    public synchronized void handleDisconnection(Socket client) {
        String nickname = socketStringMap.get(client);
        if(nickname==null) return; //if the client hasn't chosen nickname he is nor in the lobby nor in a match.
        alreadyChosenNicknames.remove(nickname);
        boolean check = false;
        for(Map.Entry<String,GameController> entry: matchesMap.entrySet()) {
            String name = entry.getKey();
            if(name.equals(nickname)) {
                check = true;
                matchesMap.get(name).handleDisconnection(nickname);
            }
        }
        if(!check)
            lobbyManager.handleDisconnection(nickname);
    }

    private void checkStart() {
        CharactersDeck.start();
        GameLimit.start();
    }
}
