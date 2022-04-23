package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;

/**
 * The Game's model interfaces. The model is observed by some virtual views. Each time the internal state
 * is changed, observers are notified. The game is updated by the {@link it.polimi.ingsw.controller.GameController} according
 * to the action performed by clients
 */
public interface IGame {
    //TODO: add character usages
    boolean startGame();
    boolean addPlayer(String name, Wizard wizard, TowerColor towerColor);
    Player nextPlayer();
    boolean removePlayer(Player player);
    Player getPlayerByName(String nickname);
    Player getPlayerByTowerColor(TowerColor towerColor);
    boolean resetStrategies();
    boolean moveMotherNature(int steps, Player player);
    boolean pickFromCloud(Player player, int cloud);
    void fillClouds();
    // todo: add other getter
}
