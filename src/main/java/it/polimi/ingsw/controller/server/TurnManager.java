package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.clouds.ShortCloud;
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
    private int studentsMoved = 1;
    private String requestName;
    private final Set<TowerColor> availableTowerColor;
    private final Set<Wizard> availableWizard;

    public enum GameState{PLANNING_ADD_TO_CLOUD,PLANNING_ASSISTANT,ACTION_MOVE,ACTION_MN,ACTION_CHOOSE_CLOUD}
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

    public void setFirstOrder(List<String> players) {
        List<String> clone = new ArrayList<>(players);
        Collections.shuffle(clone);
        playersOrder.clear();
        playersOrder.addAll(pickListFromFirst(clone.get(0),clone));
    }

    private void turn() {
        switch (gameState) {
            case PLANNING_ADD_TO_CLOUD -> {
                game.fillClouds();
                game.resetTurn();
                gameState = GameState.PLANNING_ASSISTANT;
                turn();
            }
            case PLANNING_ASSISTANT -> {
                requestName = playersOrder.get(request);
                game.setCurrentPlayer(requestName);
                controller.getVirtualView(requestName).chooseAssistant(game.getPlayableAssistant());
            }
            case ACTION_MOVE -> {
                requestName = playersOrder.get(request);
                game.setCurrentPlayer(requestName);
                controller.getVirtualView(requestName).moveStudent(game.getPlayerByName(requestName).getSchool().getEntrance().toList());
            }
            case ACTION_MN -> {
                requestName = playersOrder.get(request);
                game.setCurrentPlayer(requestName);
                controller.getVirtualView(requestName).moveMNature(game.getMotherNatureSteps(requestName));
            }
            case ACTION_CHOOSE_CLOUD -> {
                requestName = playersOrder.get(request);
                game.setCurrentPlayer(requestName);
                controller.getVirtualView(requestName).chooseCloud(game.getClouds().stream().filter(cloud -> !cloud.isEmpty()).map(ShortCloud::new).toList());
            }
        }
    }

    public void onInit() {
        availableTowerColor.addAll(GameLimit.getLimit(playersOrder.size()).getTowerColors());
        requestName = playersOrder.get(request);
        controller.getVirtualView(requestName).chooseWizardAndTowerColor(availableWizard,availableTowerColor);
    }

    public void onChosenWizAndColor(Wizard wizard, TowerColor towerColor) {
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
            request = 0;
            turn();
        }
    }

    public void onChosenAssistant() {
        if(request == playersOrder.size()-1) {
            gameState = GameState.ACTION_MOVE;
            setActionOrder(game.getPlayedAssistantMap());
            request = 0;
            turn();
        } else {
            request++;
            turn();
        }
    }

    public void onMoveStudent() {
        if(studentsMoved == game.getGameLimit().getStudentOnCloud()) {
            studentsMoved = 1;
            gameState = GameState.ACTION_MN;
            turn();
        } else {
            studentsMoved++;
            turn();
        }
    }

    public void onMoveMN() {
        gameState = GameState.ACTION_CHOOSE_CLOUD;
        turn();
    }

    public void onChosenCloud() {
        if(request == playersOrder.size()-1){
			request = 0;
            gameState = GameState.PLANNING_ADD_TO_CLOUD;
            turn();
        } else {
            gameState = GameState.ACTION_MOVE;
            request++;
            turn();
        }
    }

    public String getRequestName(){
        return requestName;
    }

    public List<String> getPlayersOrder() {
        return playersOrder;
    }

}
