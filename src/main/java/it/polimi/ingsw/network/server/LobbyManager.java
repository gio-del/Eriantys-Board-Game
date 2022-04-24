package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.server.GameController;
import it.polimi.ingsw.network.communication.notification.ChooseGameModeNotification;
import it.polimi.ingsw.view.View;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * This class handle the lobby. When client joins it is queued. If it's the first in line,
 * he is asked to provide number of player and game mode. When there's enough player a match is created.
 */
public class LobbyManager {
    private final Queue<String> players;
    private final Map<String,Connection> connectionMap;
    private final Server server;
    private boolean ready = false;
    private int nPlayers;
    private boolean isExpertMode;

    public LobbyManager(Server server) {
        this.server = server;
        this.connectionMap = Collections.synchronizedMap(new HashMap<>());
        this.players = new PriorityBlockingQueue<>();
    }

    public synchronized void addClient(String nickname,Connection connection) {
        View vv = new VirtualView(connection);
        players.add(nickname);
        connectionMap.put(nickname, connection);
        if(!ready && players.size() == 1){
            vv.chooseGameMode();
        }
        else if(ready && players.size()==nPlayers){
            GameController controller = new GameController();
            List<String> names = new ArrayList<>();
            for(String name: players){
                names.add(name);
                controller.addClient(name,connectionMap.get(name));
            }
            controller.startGame(nPlayers,isExpertMode);
            server.addMatch(names,controller);
            ready = false;
        }
    }

    public void onUpdateGameMode(ChooseGameModeNotification chooseGameModeMsg) {
        this.isExpertMode = chooseGameModeMsg.isExpertGame();
        this.nPlayers = chooseGameModeMsg.getNPlayers();
        ready = true;
    }
}
