package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.player.TowerColor;

public interface WinObserver {
    void updateBoardUsage(int size);

    void updateSackUsage(int totalElements);

    void updateTowerPlaced(TowerColor winner);
}
