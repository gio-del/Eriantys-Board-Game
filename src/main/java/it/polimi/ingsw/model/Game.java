package it.polimi.ingsw.model;

import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.model.clouds.CloudManager;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.HallManager;
import it.polimi.ingsw.model.player.*;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;
import it.polimi.ingsw.network.communication.notification.ModelUpdateNotification;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utility.Pair;
import it.polimi.ingsw.utility.character.CharactersDeck;
import it.polimi.ingsw.utility.gamelimit.GameLimit;
import it.polimi.ingsw.utility.gamelimit.GameLimitData;

import java.util.*;

/**
 * This class represents Eriantys game
 */
public class Game extends Observable {
    private final List<Player> players;
    private final List<ShortPlayer> shortPlayers;
    private final Board board;
    private final Sack sack;
    private final HallManager hallManager;
    private final Bank bank;
    private final List<CharacterCard> characterInUse;
    private final List<Pair<String, Assistant>> playedAssistantMap;
    private CloudManager clouds;
    private Player currentPlayer;
    private GameLimitData gameLimitData;
    private boolean expertMode;
    private boolean alreadyPlayedACharacter;
    private int stepsIncrement;

    /**
     * The class that represents Eriantys game
     */
    public Game() {
        this.players = new ArrayList<>();
        this.shortPlayers = new ArrayList<>();
        this.board = new Board();
        this.sack = new Sack();
        this.playedAssistantMap = new ArrayList<>();
        this.bank = new Bank();
        this.hallManager = new HallManager(bank);
        this.characterInUse = CharactersDeck.extractCharacterInUse();
    }

    /**
     * Initialize player, game limit, clouds and hall manager.
     */
    public void init() {
        gameLimitData = GameLimit.getLimit(shortPlayers.size());
        for (ShortPlayer shortPlayer : shortPlayers) {
            Player player = new Player(shortPlayer, gameLimitData, hallManager);
            this.hallManager.addPlayer(player);
            this.players.add(player);
        }
        this.clouds = new CloudManager(players.size(), gameLimitData.getStudentOnCloud());
    }

    /**
     * Starts the game filling island and the entrance of each player
     */
    public void startGame(boolean expertMode) {
        init();
        sack.initialFill();
        board.initIslands(sack);
        sack.fill();
        this.expertMode = expertMode;
        for (Player player : players)
            player.initialEntranceFill(sack.extractListOfPawns(gameLimitData.getMaxEntrance()));
        if (expertMode)
            characterInUse.forEach(character -> character.fill(sack));
        notifyObserver(new ModelUpdateNotification(new ShortModel(this, expertMode)));
    }

    /**
     * @param name       name of the player
     * @param wizard     wizard chosen by the player
     * @param towerColor color chosen by the player
     */
    public void addPlayer(String name, Wizard wizard, TowerColor towerColor) {
        if (shortPlayers.stream().noneMatch(shortPlayer -> shortPlayer.name().equals(name))) {
            ShortPlayer shortPlayer = new ShortPlayer(name, wizard, towerColor);
            this.shortPlayers.add(shortPlayer);
        }
    }


    /**
     * @return the next player going to play
     */
    public String nextPlayer() {

        int index = players.indexOf(currentPlayer);

        if (index == (players.size() - 1)) {
            currentPlayer = players.get(0);

        } else {
            currentPlayer = players.get(index + 1);
        }
        return currentPlayer.getPlayerName();
    }

    /**
     * @param nickname look for the nickname in the actual players
     * @return the player if is found, {@code null} otherwise
     */
    public Player getPlayerByName(String nickname) {
        for (Player player : players) {
            if (player.getPlayerName().equals(nickname)) {
                return player;
            }
        }
        return null;
    }

    /**
     * To find the player with a specific TowerColor
     *
     * @param towerColor of the player to be found
     * @return the player
     */
    public Player getPlayerByTowerColor(TowerColor towerColor) {
        for (Player player : players) {
            if (player.getColor().equals(towerColor)) {
                return player;
            }
        }
        return null;
    }

    /**
     * To reset all the strategies to the standard ones
     */
    public void resetStrategies() {
        board.resetStrategy();
        hallManager.resetStrategy();
    }

    /**
     * Pays the cost of the chosen character, refills it with students and notify the observer the changes.
     *
     * @param character the chosen character
     */
    public void useCharacter(CharacterCard character) {
        alreadyPlayedACharacter = true;
        int cost = character.getCost() + (character.hasCoinOn() ? 1 : 0);
        bank.pay(currentPlayer, cost);
        if (!character.hasCoinOn()) character.setCoinOn(true);
        character.fill(sack);
        notifyObserver(new ModelUpdateNotification(new ShortModel(this, expertMode)));
    }

    /**
     * @param character the chosen character
     * @return true if the character can be played by the current player, otherwise false
     */
    public boolean canUseCharacter(CharacterCard character) {
        int cost = character.getCost() + (character.hasCoinOn() ? 1 : 0);
        return expertMode && !alreadyPlayedACharacter && bank.canPay(currentPlayer, cost);
    }

    /**
     * Check if is possible to move motherNature of specified steps
     *
     * @param steps  that the player wants motherNature to do
     * @param player going to perform the action
     * @return true and perform the movement, false if not possible
     */
    public boolean moveMotherNature(int steps, Player player) {
        int maxMove = player.getLastPlayedAssistant().movement() + stepsIncrement;
        if (steps <= maxMove && steps > 0) {
            board.moveMotherNature(steps);
            if (board.getMotherNatureIsland().getBanTiles() > 0) {
                characterInUse.stream().filter(CharacterCard::isBanCard).forEach(CharacterCard::addBanTiles);
                board.getMotherNatureIsland().removeBanTiles();
            } else
                board.calculateInfluence(players);
            notifyObserver(new ModelUpdateNotification(new ShortModel(this, expertMode)));
            return true;
        } else return false;
    }

    /**
     * Pick pawns from the chosen cloud and put it in current player's entrance
     *
     * @param cloud the chosen cloud
     * @return true if adding to the entrance is ok, otherwise false
     */
    public boolean pickFromCloud(int cloud) {
        Cloud cloudChosen = clouds.getSpecificCloud(cloud);
        if (cloudChosen != null) {
            boolean check = currentPlayer.addPawnsFromCloud(cloudChosen);
            notifyObserver(new ModelUpdateNotification(new ShortModel(this, expertMode)));
            return check;
        }
        return false;
    }

    /**
     * Fill the clouds according to the game limit, this is also used at the beginning of each turn
     */
    public void fillClouds() {
        clouds.fillClouds(sack);

        notifyObserver(new ModelUpdateNotification(new ShortModel(this, expertMode)));
    }

    /**
     * Play assistant for the current player
     */
    public void playAssistant(Assistant assistant) {
        currentPlayer.playAssistant(assistant);
        playedAssistantMap.add(new Pair<>(currentPlayer.getPlayerName(), assistant));
        notifyObserver(new ModelUpdateNotification(new ShortModel(this, expertMode)));
    }

    /**
     * Move a pawn from the entrance to the hall of the currentPlayer
     *
     * @param pawnColor to be moved to the hall
     */
    public void moveFromEntranceToHall(PawnColor pawnColor) {
        currentPlayer.moveFromEntranceToHall(pawnColor);
        notifyObserver(new ModelUpdateNotification(new ShortModel(this, expertMode)));
    }

    /**
     * Move a pawn from the entrance of the current Player to a chosen island
     *
     * @param pawnColor to be moved to the island
     * @param island    the chosen island
     */
    public void moveFromEntranceToIsland(PawnColor pawnColor, int island) {
        currentPlayer.moveFromEntranceToIsland(new Pawns(pawnColor), board.getIslands().get(island));
        notifyObserver(new ModelUpdateNotification(new ShortModel(this, expertMode)));
    }

    public int numOfPlayer() {
        return shortPlayers.size();
    }

    /**
     * @return a sets of {@link Assistant} that can be played by the current player
     */
    public Set<Assistant> getPlayableAssistant() {
        Set<Assistant> assistants = EnumSet.allOf(Assistant.class);
        for (Assistant assistant : Assistant.values())
            if (!currentPlayer.getHand().contains(assistant)) assistants.remove(assistant);
        List<Assistant> playedAssistant = playedAssistantMap.stream().map(Pair::second).toList();
        playedAssistant.forEach(assistants::remove);
        if (assistants.isEmpty())
            return currentPlayer.getHand(); //a player has only played assistant, in this case it's ok to play them
        return assistants;
    }

    /**
     * @param nickname the requested nickname
     * @return the {@link Assistant} played by the requested player if present, otherwise {@code null}
     */
    public Assistant getPlayedAssistantByName(String nickname) {
        for (Pair<String, Assistant> assistantPair : playedAssistantMap) {
            if (assistantPair.first().equals(nickname)) {
                return assistantPair.second();
            }
        }
        return null;
    }

    public Map<String, Assistant> assistantMap() {
        Map<String, Assistant> map = new HashMap<>();
        for (Pair<String, Assistant> assistantPair : playedAssistantMap) {
            map.put(assistantPair.first(), assistantPair.second());
        }
        return map;
    }

    /**
     * @param nickname the requested nickname
     * @return the number of steps that the requested player can perform to mother nature
     */
    public int getMotherNatureSteps(String nickname) {
        return getPlayedAssistantByName(nickname).movement() + stepsIncrement;
    }

    /**
     * When a turn is completed the map of the played assistant is cleared
     */
    public void endTurn() {
        playedAssistantMap.clear();
    }

    /**
     * Before the beginning of the turn of each player, the strategies in use are reset to the default ones.
     */
    public void prepareNextTurn() {
        this.alreadyPlayedACharacter = false;
        this.stepsIncrement = 0;
        resetStrategies();
    }


    /**
     * @return list of players in the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return the board of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return the sack of the game
     */
    public Sack getSack() {
        return sack;
    }

    /**
     * @return the clouds
     */
    public List<Cloud> getClouds() {
        return clouds.getClouds();
    }

    /**
     * @return the current playing character
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String player) {
        currentPlayer = getPlayerByName(player);
    }

    /**
     * @return the character available in this game
     */
    public List<CharacterCard> getCharacterInUse() {
        return characterInUse;
    }

    public ProfessorAssignor getProfessorAssignor() {
        return hallManager.getProfessorAssignor();
    }

    public Bank getBank() {
        return bank;
    }

    public List<Pair<String, Assistant>> getPlayedAssistantMap() {
        return playedAssistantMap;
    }

    public GameLimitData getGameLimit() {
        return gameLimitData;
    }

    public void setStepsIncrement(int increment) {
        this.stepsIncrement = increment;
    }
}
