package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.utility.gamelimit.GameLimit;

import java.util.*;

public class TurnManager {
    private final List<String> playersOrder;
    private final Game game;
    private final GameController controller;
    private int request = 0;
    private String requestName;
    private final Set<TowerColor> availableTowerColor;
    private final Set<Wizard> availableWizard;

    public enum GameState{INIT,PLANNING_ADD_TO_CLOUD,PLANNING_ASSISTANT,ACTION_MOVE,ACTION_MN,ACTION_CHOOSE_CLOUD, WAIT}
    private GameState gameState;
    public TurnManager(Game game, GameController controller) {
        this.availableWizard = EnumSet.allOf(Wizard.class);
        this.availableTowerColor = new HashSet<>();
        this.game = game;
        this.controller = controller;
        playersOrder = new ArrayList<>();
    }

    /**
     * Set the order to the action one according to the played assistant (ascendant card's value order)
     * @param nicknameMapAssistant a list of relation between player and played assistant
     */
    public void setActionOrder(List<Pair<String,Assistant>> nicknameMapAssistant) {
        List<Pair<String,Assistant>> sortedList = nicknameMapAssistant.stream().sorted(Comparator.comparingInt(o -> o.second().value())).toList();
        List<String> order = sortedList.stream().map(Pair::first).toList();
        playersOrder.clear();
        playersOrder.addAll(order);
    }

    /**
     * Set the order to the planning one according to the played assistant (the lowest card's value and so clockwise)
     * @param nicknameMapAssistant a list of relation between player and played assistant
     * @param players the order of player at the table
     */
    public void setPlanningOrder(List<Pair<String,Assistant>> nicknameMapAssistant, List<String> players) {
        String minValuePlayer = nicknameMapAssistant.stream().min(Comparator.comparingInt(o -> o.second().value())).map(Pair::first).orElse("");
            List<String> order = pickListFromFirst(minValuePlayer,players);
            playersOrder.clear();
            playersOrder.addAll(order);
        }

    private List<String> pickListFromFirst(String first, List<String> players) {
        List<String> order = new ArrayList<>();
        if(players.contains(first)) {
            int pos = players.indexOf(first);
            int index;
            if (pos != -1) {
                int i = 0;
                while (i < players.size()) {
                    index = pos + i;
                    if (index >= players.size()) {
                        index = pos + i - players.size();
                    }
                    order.add(players.get(index));
                    i++;
                }
            }
        }
        return order;
    }

    public void setFirstOrder(List<String> players){
        List<String> clone = new ArrayList<>(players);
        Collections.shuffle(clone);
        playersOrder.clear();
        playersOrder.addAll(pickListFromFirst(clone.get(0),players));
    }

    public List<String> getPlayersOrder() {
        return playersOrder;
    }

    public void onInit() {
        gameState = GameState.INIT;
        availableTowerColor.addAll(GameLimit.getLimit(playersOrder.size()).getTowerColors());
        requestName = playersOrder.get(request);
        controller.getVirtualView(requestName).chooseWizardAndTowerColor(availableWizard,availableTowerColor);
    }

    public void onChooseWizAndColor(Wizard wizard, TowerColor towerColor) {
        if(!controller.gameReady()) {
            availableWizard.remove(wizard);
            availableTowerColor.remove(towerColor);
            request++;
            requestName = playersOrder.get(request);
            controller.getVirtualView(requestName).chooseWizardAndTowerColor(availableWizard, availableTowerColor);
        }
        else {
            game.startGame();
            gameState = GameState.PLANNING_ADD_TO_CLOUD;
            turn();
        }
    }

    private void turn(){
        switch (gameState){
            case PLANNING_ADD_TO_CLOUD -> {
                game.fillClouds();
                gameState = GameState.PLANNING_ASSISTANT;
            }
            case PLANNING_ASSISTANT -> {
                request = 0;
                requestName = playersOrder.get(request);
            }
            case ACTION_MOVE -> {
                request = 0;
                requestName = playersOrder.get(request);
                controller.getVirtualView(requestName).moveStudent(game.getPlayerByName(requestName).getSchool().getEntrance().toList());
            }
            case ACTION_MN -> {
                request = 0;
                requestName = playersOrder.get(request);
            }
        }
    }

    public void onMoveStudent() {

    }

    public String getRequestName(){
        return requestName;
    }
}
