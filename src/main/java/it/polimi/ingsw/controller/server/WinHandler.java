package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.observer.WinObserver;

import java.util.*;

/**
 * This class observer game component that can trigger a win condition (e.g. the sack, the board of islands, the schools of each player ecc.).
 * When a win condition occurs, the game can end immediately or at the end of the current turn,
 */
public class WinHandler implements WinObserver {
    private final TurnManager turn;
    private final GameController controller;
    private final Game model;

    public WinHandler(GameController controller, Game model) {
        this.controller = controller;
        this.model = model;
        this.turn = controller.getTurnManager();
    }

    /**
     * Update about the number of islands in the game
     *
     * @param size the size of the board
     */
    @Override
    public void updateBoardUsage(int size) {
        if (size <= Constants.MIN_ISLAND)
            handleWin();
    }

    /**
     * Update about the number of students in the sack
     *
     * @param totalElements the remaining pawns in the sack
     */
    @Override
    public void updateSackUsage(int totalElements) {
        if (totalElements == 0)
            turn.setLastTurn(true);
    }

    /**
     * Update when a tower is placed by a player
     *
     * @param winner the tower color of the player that conquest an island
     */
    @Override
    public void updateTowerPlaced(TowerColor winner) {
        if (model.getPlayerByTowerColor(winner).getTowerNum() <= 0)
            handleWin();
    }

    /**
     * Extract the winner, the player with the minimum number of towers. If there are more than one, professors number is checked.
     */
    public void handleWin() {
        Map<String, Integer> playerMapTowerNum = new HashMap<>();
        List<String> names = model.getPlayers().stream().map(Player::getPlayerName).toList();
        for (String name : names) {
            playerMapTowerNum.put(name, model.getPlayerByName(name).getTowerNum());
        }
        int minValue = playerMapTowerNum.values().stream().min(Comparator.naturalOrder()).orElse(model.getGameLimit().getNumberOfTower());
        List<String> winners = playerMapTowerNum.entrySet().stream().filter(entry -> entry.getValue() == minValue).map(Map.Entry::getKey).toList();
        if (winners.size() > 1)
            handleWinProfessor(winners);
        else
            controller.handleWin(winners.get(0));
    }

    /**
     * Extract "winners" list, the players with the maximum professor in the school. If there are more than one, a winner is randomly extracted
     *
     * @param winners the list of players that has the minimum number of tower
     */
    private void handleWinProfessor(List<String> winners) {
        Map<String, Integer> playerMapProfNum = new HashMap<>();
        for (String name : winners)
            playerMapProfNum.put(name, model.getPlayerByName(name).getSchool().getProfessorTable().totalElements());
        int maxValue = playerMapProfNum.values().stream().max(Comparator.naturalOrder()).orElse(0);
        List<String> profWinners = playerMapProfNum.entrySet().stream().filter(entry -> entry.getValue() == maxValue).map(Map.Entry::getKey).toList();
        if (profWinners.size() > 1)
            randomWinner(profWinners);
        else
            controller.handleWin(profWinners.get(0));
    }

    /**
     * Extract a random player that win the match
     *
     * @param profWinners the players that have even the same number of professors
     */
    private void randomWinner(List<String> profWinners) {
        List<String> copy = new ArrayList<>(profWinners);
        Collections.shuffle(copy);
        controller.handleWin(copy.get(0));
    }
}
