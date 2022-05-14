package it.polimi.ingsw.model;

import it.polimi.ingsw.model.character.ShortCharacter;
import it.polimi.ingsw.network.communication.notification.*;
import it.polimi.ingsw.utility.gamelimit.GameLimit;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.player.*;
import it.polimi.ingsw.utility.character.CharactersDeck;
import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.model.clouds.CloudManager;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.HallManager;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utility.gamelimit.GameLimitData;
import it.polimi.ingsw.utility.Pair;

import java.util.*;

/**
 * This class represents Eriantys game
 */
public class Game extends Observable {
    private final List<Player> players;
    private final List<ShortPlayer> shortPlayers;
    private final Board board;
    private final Sack sack;
    private CloudManager clouds;
    private Player currentPlayer;
    private final HallManager hallManager;
    private final Bank bank;
    private final List<CharacterCard> characterInUse;
    private final List<Pair<String,Assistant>> playedAssistantMap;
    private GameLimitData gameLimitData;

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
        for(ShortPlayer shortPlayer: shortPlayers) {
            Player player = new Player(shortPlayer,gameLimitData,hallManager);
            this.hallManager.addPlayer(player);
            this.players.add(player);
        }
        this.clouds = new CloudManager(players.size(), gameLimitData.getStudentOnCloud());
    }

    /**
     * Starts the game filling island and the entrance of each player
     */
    public void startGame(boolean isExpertMode) {
        init();
        sack.initialFill();
        board.initIslands(sack);
        sack.fill();
        for (Player player : players) {
            player.initialEntranceFill(sack.extractListOfPawns(gameLimitData.getMaxEntrance()));
            notifyObserver(new SchoolNotification(new ShortSchool(player.getSchool()), player.getPlayerName()));
        }
        if(isExpertMode) {
            characterInUse.forEach(character -> character.fill(sack));
            notifyObserver(new CharacterNotification(characterInUse.stream().map(ShortCharacter::new).toList()));
        }
        notifyObserver(new BoardNotification(new ShortBoard(board)));
    }

    /**
     * @param name       name of the player
     * @param wizard     wizard chosen by the player
     * @param towerColor color chosen by the player
     */
    public void addPlayer(String name, Wizard wizard, TowerColor towerColor) {
        if (shortPlayers.stream().noneMatch(shortPlayer -> shortPlayer.playerName().equals(name))) {
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
     *
     * @return true when done
     */
    public boolean resetStrategies() {
        board.resetStrategy();
        hallManager.resetStrategy();
        return true;
    }

    public void useCharacter(CharacterCard character) {
        int cost = character.getCost() + (character.hasCoinOn() ? 1 : 0);
        bank.pay(currentPlayer,cost);
        if(!character.hasCoinOn()) character.setCoinOn(true);
        character.fill(sack);
        notifyObserver(new ModelUpdateNotification(new ShortModel(this)));

    }

    public boolean canUseCharacter(CharacterCard character) {
        int cost = character.getCost() + (character.hasCoinOn() ? 1 : 0);
        return bank.canPay(currentPlayer, cost);
    }

    /**
     * Check if is possible to move motherNature of specified steps
     *
     * @param steps  that the player wants motherNature to do
     * @param player going to perform the action
     * @return true and perform the movement, false if not possible
     */
    public boolean moveMotherNature(int steps, Player player) {
        // TODO: remove player and substitute with current player.
        // TODO: implement card to add 2 more possible steps, add a Mover class with a limit standard strategy etc.
        int maxMove = player.getLastPlayedAssistant().movement();
        if (steps <= maxMove && steps > 0) {
            board.moveMotherNature(steps, players);
            notifyObserver(new BoardNotification(new ShortBoard(board)));
            return true;
        } else return false;
    }

    /**
     * Pick pawns from the chosen cloud and put it in current player's entrance
     * @param cloud  the chosen cloud
     * @return true if adding to the entrance is ok, otherwise false
     */
    public boolean pickFromCloud(int cloud) {
        Cloud cloudChosen = clouds.getSpecificCloud(cloud);
        if (cloudChosen != null) {
            boolean check = currentPlayer.addPawnsFromCloud(cloudChosen);
            notifyObserver(new SchoolNotification(new ShortSchool(currentPlayer.getSchool()),currentPlayer.getPlayerName()));
            notifyObserver(new CloudsNotification(clouds.getClouds().stream().map(ShortCloud::new).toList()));
            return check;
        }
        return false;
    }

    /**
     * Fill the clouds according to the game limit, this is also used at the beginning of each turn
     */
    public void fillClouds() {
        clouds.fillClouds(sack);
        List<ShortCloud> update = clouds.getClouds().stream().map(ShortCloud::new).toList();
        notifyObserver(new CloudsNotification(update));
    }

    /**
     * Play assistant for the current player
     */
    public void playAssistant(Assistant assistant) {
        currentPlayer.playAssistant(assistant);
        playedAssistantMap.add(new Pair<>(currentPlayer.getPlayerName(),assistant));
    }

    /**
     * Move a pawn from the entrance to the hall of the currentPlayer
     *
     * @param pawnColor to be moved to the hall
     */
    public void moveFromEntranceToHall(PawnColor pawnColor) {
        currentPlayer.moveFromEntranceToHall(pawnColor);
        for(Player player: players){ //todo: send one notification only with all the schools!!
            notifyObserver(new SchoolNotification(new ShortSchool(player.getSchool()),player.getPlayerName()));
        }
    }

    /**
     * Move a pawn from the entrance of the current Player to a chosen island
     *
     * @param pawnColor to be moved to the island
     * @param island    the chosen island
     */
    public void moveFromEntranceToIsland(PawnColor pawnColor, int island) {
        currentPlayer.moveFromEntranceToIsland(new Pawns(pawnColor), board.getIslands().get(island));
        notifyObserver(new SchoolNotification(new ShortSchool(currentPlayer.getSchool()), currentPlayer.getPlayerName()));
        notifyObserver(new BoardNotification(new ShortBoard(board))); //todo: send one notification only
    }

    public int numOfPlayer() {
        return shortPlayers.size();
    }

    public Set<Assistant> getPlayableAssistant() {
        Set<Assistant> assistants = EnumSet.allOf(Assistant.class);
        for(Assistant assistant: Assistant.values())
            if (!currentPlayer.getHand().contains(assistant)) assistants.remove(assistant);
        List<Assistant> playedAssistant = playedAssistantMap.stream().map(Pair::second).toList();
        playedAssistant.forEach(assistants::remove);
        if(assistants.isEmpty()) return currentPlayer.getHand(); //a player has only played assistant, in this case it's ok to play them
        return assistants;
    }

    public Assistant getPlayedAssistantByName(String requestName) {
        for(Pair<String,Assistant> assistantMap: playedAssistantMap){
            if(assistantMap.first().equals(requestName)){
                return assistantMap.second();
            }
        }
        return null;
    }

    public int getMotherNatureSteps(String requestName) {
        //TODO: implement limitation of mother nature steps with default strategy and character
        return getPlayedAssistantByName(requestName).movement();
    }

    public void resetTurn() {
        playedAssistantMap.clear();
        resetStrategies();
    }

    /**
     * @return list of players
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

    /**
     * @return the character available in this game
     */
    public List<CharacterCard> getCharacterInUse() {
        return characterInUse;
    }

    public void setCurrentPlayer(String player) {
        currentPlayer = getPlayerByName(player);
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
}
