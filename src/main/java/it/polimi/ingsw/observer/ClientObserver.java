package it.polimi.ingsw.observer;

/**
 * The client observer implements this interface.
 */
public interface ClientObserver {
    void updateConnection(String ip, int port);
    void updateNickname(String nickname);
    void updateGameModeNumPlayer(String mode, int numOfPlayer);
    //todo: add other update
}
