package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.player.TowerColor;

/**
 * This class is implemented by the {@link it.polimi.ingsw.controller.server.WinHandler}. It is used to check the win conditions.
 */
public interface WinObserver {
    /**
     * When the board is used (e.g. mother nature is moved) the observer receive an update regarding the new size of the board
     *
     * @param size the number of island in the board
     */
    void updateBoardUsage(int size);

    /**
     * When the sack is used (a student is extracted) the observer receive an update regarding the new size of the board
     *
     * @param totalElements the number of students in the sack
     */
    void updateSackUsage(int totalElements);

    /**
     * When a tower is placed on an island the observer receive an update regarding the color of the player who placed that tower in order to check if he won.
     *
     * @param winner the player who added the tower on an island
     */
    void updateTowerPlaced(TowerColor winner);
}
