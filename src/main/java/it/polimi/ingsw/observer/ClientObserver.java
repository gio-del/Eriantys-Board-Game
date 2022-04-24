package it.polimi.ingsw.observer;

public interface ClientObserver {
    void updateConnection(String ip, int port);
    void updateNickname(String nickname);
}
