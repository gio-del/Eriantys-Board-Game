package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.character.ActionVisitor;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.utility.gamelimit.GameLimit;

import java.util.*;

public class TurnManager {
    private final List<String> playersOrder;
    private final Game game;
    private final GameController controller;
    private final Set<TowerColor> availableTowerColor;
    private final Set<Wizard> availableWizard;
    private int request = 0;
    private int studentsMoved = 1;
    private int characterRequest = 0;
    private String requestName;
    private boolean isLastTurn = false;
    private GameState gameState;
    private GameState callbackState;
    private CharacterCard chosenCard;
    public TurnManager(Game game, GameController controller) {
        this.availableWizard = EnumSet.allOf(Wizard.class);
        this.availableTowerColor = new HashSet<>();
        this.game = game;
        this.controller = controller;
        playersOrder = new ArrayList<>();
    }

    /**
     * Set the order to the action one according to the played assistant (ascendant card's value order)
     *
     * @param nicknameMapAssistant a list of relation between player and played assistant
     */
    public void setActionOrder(List<Pair<String, Assistant>> nicknameMapAssistant) {
        List<Pair<String, Assistant>> sortedList = nicknameMapAssistant.stream().sorted(Comparator.comparingInt(o -> o.second().value())).toList();
        List<String> order = sortedList.stream().map(Pair::first).toList();
        playersOrder.clear();
        playersOrder.addAll(order);
    }

    /**
     * Set the order to the planning one according to the played assistant (the lowest card's value and so clockwise)
     *
     * @param nicknameMapAssistant a list of relation between player and played assistant
     * @param players              the order of player at the table
     */
    public void setPlanningOrder(List<Pair<String, Assistant>> nicknameMapAssistant, List<String> players) {
        String minValuePlayer = nicknameMapAssistant.stream().min(Comparator.comparingInt(o -> o.second().value())).map(Pair::first).orElse("");
        List<String> order = pickListFromFirst(minValuePlayer, players);
        playersOrder.clear();
        playersOrder.addAll(order);
    }

    private List<String> pickListFromFirst(String first, List<String> players) {
        List<String> order = new ArrayList<>();
        if (players.contains(first)) {
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
        playersOrder.addAll(pickListFromFirst(clone.get(0), clone));
    }

    //todo: state pattern
    private void turn() {
        switch (gameState) {
            case PLANNING_ADD_TO_CLOUD -> {
                game.fillClouds();
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
                controller.getVirtualView(requestName).chooseCloud(game.getClouds().stream().map(ShortCloud::new).toList());
            }
            case USE_CHARACTER -> {
                if (characterRequest < chosenCard.getRequires().size())
                    askRequirements(chosenCard.getRequires().get(characterRequest));
                else
                    chosenCard.getAction().accept(new ActionVisitor(this, game, chosenCard));
            }
        }
    }

    public void onInit() {
        availableTowerColor.addAll(GameLimit.getLimit(playersOrder.size()).getTowerColors());
        requestName = playersOrder.get(request);
        controller.getVirtualView(requestName).chooseWizardAndTowerColor(availableWizard, availableTowerColor);
    }

    public void onChosenWizAndColor(Wizard wizard, TowerColor towerColor) {
        if (!controller.gameReady()) {
            availableWizard.remove(wizard);
            availableTowerColor.remove(towerColor);
            request++;
            requestName = playersOrder.get(request);
            controller.getVirtualView(requestName).chooseWizardAndTowerColor(availableWizard, availableTowerColor);
        } else {
            game.startGame(controller.isExpertMode());
            controller.startMatch();
            gameState = GameState.PLANNING_ADD_TO_CLOUD;
            request = 0;
            turn();
        }
    }

    public void onChosenAssistant() {
        if (game.getPlayerByName(requestName).getHand().isEmpty())
            isLastTurn = true;
        if (request == playersOrder.size() - 1) {
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
        if (studentsMoved == game.getGameLimit().getStudentOnCloud()) {
            studentsMoved = 1;
            gameState = GameState.ACTION_MN;
            turn();
        } else {
            studentsMoved++;
            turn();
        }
    }

    public void onIncorrectIsland() {
        controller.getVirtualView(requestName).showMessage("Island provided is not correct.");
        turn();
    }

    public void onMoveMN() {
        gameState = GameState.ACTION_CHOOSE_CLOUD;
        turn();
    }

    public void onChosenCloud() {
        game.prepareNextTurn();
        if (request == playersOrder.size() - 1 && !isLastTurn) {
            request = 0;
            gameState = GameState.PLANNING_ADD_TO_CLOUD;
            setPlanningOrder(game.getPlayedAssistantMap(), game.getPlayers().stream().map(Player::getPlayerName).toList());
            game.endTurn();
            turn();
        } else if (request == playersOrder.size() - 1 && isLastTurn) {
            controller.getWinHandler().handleWin();
        } else {
            gameState = GameState.ACTION_MOVE;
            request++;
            turn();
        }
    }

    public void onChosenCharacter(CharacterCard character) {
        //todo: add control on name of the user of this character, it must be the only character played by him/her
        callbackState = gameState;
        gameState = GameState.USE_CHARACTER;
        if (character != null && game.canUseCharacter(character)) {
            this.chosenCard = character;
            turn();
        } else {
            onActionFailed();
        }
    }

    public void onChosenInvalidCharacter() {
        controller.getVirtualView(requestName).showMessage("Chosen character is not valid.");
        turn();
    }

    private void askRequirements(String requirement) {
        switch (requirement) {
            case "color" -> controller.getVirtualView(requestName).askColor();
            case "island" -> controller.getVirtualView(requestName).askIsland();
            case "swap" -> {
                characterRequest++;
                int swap = Integer.parseInt(chosenCard.getRequires().get(characterRequest));
                controller.getVirtualView(requestName).askSwapList(swap);
            }
            default -> chosenCard.getAction().accept(new ActionVisitor(this, game, chosenCard)); //no inputs needed
        }
    }

    public void onChooseIsland(Island island) {
        chosenCard.setChosenIsland(island);
        characterRequest++;
        turn();
    }

    public void onChosenColor(PawnColor chosen) {
        chosenCard.setChosenColor(chosen);
        characterRequest++;
        turn();
    }

    public void onChosenSwapList(List<PawnColor> swapList) {
        chosenCard.setChosenSwap(swapList);
        characterRequest++;
        turn();
    }


    public void onActionCompleted() {
        characterRequest = 0;
        game.useCharacter(chosenCard);
        gameState = callbackState;
        turn();
    }

    public void onActionFailed() {
        characterRequest = 0;
        controller.getVirtualView(requestName).showMessage("Not enough money to play this character or incorrect input has been provided.");
        gameState = callbackState;
        turn();
    }

    public String getRequestName() {
        return requestName;
    }

    public List<String> getPlayersOrder() {
        return playersOrder;
    }

    public boolean isLastTurn() {
        return isLastTurn;
    }

    public void setLastTurn(boolean lastTurn) {
        isLastTurn = lastTurn;
    }

    public enum GameState {PLANNING_ADD_TO_CLOUD, PLANNING_ASSISTANT, ACTION_MOVE, ACTION_MN, ACTION_CHOOSE_CLOUD, USE_CHARACTER}
}
