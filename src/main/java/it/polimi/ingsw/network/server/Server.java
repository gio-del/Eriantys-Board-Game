package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.network.communication.notification.ChooseGameModeNotification;
import it.polimi.ingsw.network.communication.notification.NicknameErrorNotification;
import it.polimi.ingsw.network.communication.notification.Notification;

import java.io.IOException;
import java.net.ServerSocket;
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
    public static final Logger logger = Logger.getLogger(Server.class.getSimpleName());
    public static final String NAME = "server";
    private static final Set<String> alreadyChosenNicknames = new HashSet<>();
    private ServerSocket serverSocket;
    private final LobbyManager lobbyManager;

    public Server(int port) {
        lobbyManager = new LobbyManager(this);
        matchesMap = Collections.synchronizedMap(new HashMap<>());
        try {
            serverSocket = new ServerSocket(port);
            log("Server started on port " + port + ".");
        } catch (IOException e) {
            log("Server couldn't start");
            exit(0);
        }
    }

    public void start(){
        new Thread(new ServerThread(serverSocket,this)).start();
    }

    /**
     * When a client join it is added to the {@link LobbyManager}
     * @param connection to be added
     */
    public synchronized void addClient(String nickname,Connection connection) {
        if(alreadyChosenNicknames.contains(nickname)){
            Notification msg = new NicknameErrorNotification();
            msg.setClientId(Server.NAME);
            connection.sendMessage(msg);
            return;
        }
        log(nickname + " joined the lobby");
        alreadyChosenNicknames.add(nickname);
        lobbyManager.addClient(nickname, connection);
    }

    public synchronized void addMatch(List<String> names,GameController controller){
        log("A match is started!");
        names.forEach(name -> matchesMap.put(name,controller));
    }

    public void receiveMessage(Notification msg){
        if(msg instanceof ChooseGameModeNotification chooseGameModeMsg){
            lobbyManager.onUpdateGameMode(chooseGameModeMsg);
        }
        else
            matchesMap.get(msg.getClientID()).handleMessage(msg);
    }

    public void log(String msg){
        logger.info(msg);
    }
}
