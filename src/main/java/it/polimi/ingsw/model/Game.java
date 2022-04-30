package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.CharactersDeck;
import it.polimi.ingsw.model.character.action.ActionType;
import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.model.clouds.CloudManager;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.BankHallManager;
import it.polimi.ingsw.model.place.HallManager;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;
import it.polimi.ingsw.network.communication.notification.BoardNotification;
import it.polimi.ingsw.network.communication.notification.CloudsNotification;
import it.polimi.ingsw.network.communication.notification.SchoolNotification;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents Eriantys game
 */
public class Game extends Observable {
    private final List<Player> players;
    private final Board board;
    private final Sack sack;
    private final CloudManager clouds;
    private Player currentPlayer;
    private final HallManager hallManager;
    private final Bank bank;
    private final GameLimit gameLimit;
    private final int nPlayers;
    private final boolean isExpertMode;
    private List<CharacterCard> characterInUse;
    private final List<Wizard> alreadyChoiceWizard;
    private final List<TowerColor> alreadyChoiceTowerColor;
    private final Map<Player,Integer> playerMapAssistantMovement;
    private final Map<Player,Integer> playerMapAssistantValue;


    /**
     * The Game is created with the number of players by the controller, the limit of the Game are set
     * @param nPlayers the number of players
     * @param isExpertMode the game mode
     */
    public Game(int nPlayers, boolean isExpertMode) {
        this.players = new ArrayList<>();
        this.nPlayers = nPlayers;
        gameLimit = new GameLimit(nPlayers==3);
        this.board = new Board();
        this.sack = new Sack();
        this.alreadyChoiceWizard = new ArrayList<>();
        this.alreadyChoiceTowerColor = new ArrayList<>();
        playerMapAssistantMovement = new HashMap<>();
        playerMapAssistantValue = new HashMap<>();
        this.clouds = new CloudManager(nPlayers, gameLimit.getStudentOnCloud());
        this.bank = new Bank();
        this.hallManager = (isExpertMode)?new BankHallManager(bank):new HallManager();
        this.isExpertMode = isExpertMode;
        this.characterInUse = new ArrayList<>();
    }

    /**
     * start game
     * @return true if game is started, false otherwise
     */
    public boolean startGame() {
        if (players.size() != nPlayers) return false;
        if(isExpertMode) characterInUse = new CharactersDeck().extractCharacterInUse();
        if(characterInUse.size()!= Constants.CHARACTER_IN_USE) return false;
        sack.initialFill();
        board.initIslands(sack);
        sack.fill();
        for (Player player : players) {
            player.initialEntranceFill(sack.extractListOfPawns(gameLimit.getMaxEntrance()));
        }
        return true;
    }

    /**
     *
     * @param name name of the player
     * @param wizard wizard chosen by the player
     * @param towerColor color chosen by the player
     * @return true if player was added, otherwise false.
     */
    public boolean addPlayer(String name,Wizard wizard, TowerColor towerColor){
        if(players.stream().anyMatch(player -> player.getPlayerName().equals(name)) ||
                alreadyChoiceWizard.contains(wizard) ||
                alreadyChoiceTowerColor.contains(towerColor) || //todo: check this for 4 player matches
                this.players.size() >= this.nPlayers) {
            return false;
        }
        else{
            Player player = new Player(name,wizard,towerColor,gameLimit, hallManager);
            alreadyChoiceWizard.add(player.getWizard());
            alreadyChoiceTowerColor.add(player.getColor());
            this.players.add(player);
            this.hallManager.addPlayer(player);
            return true;
        }
    }


    /**
     *
     * @return the next player going to play
     */
    public Player nextPlayer(){

        int index = players.indexOf(currentPlayer);

        if (index == (players.size() - 1)) {
            currentPlayer = players.get(0);

        } else {
            currentPlayer = players.get(index + 1);
        }
        return currentPlayer;
    }

    /**
     *
     * @param player to be removed
     * @return {@code true} if ok
     */
    public boolean removePlayer(Player player){
        alreadyChoiceWizard.remove(player.getWizard());
        return players.remove(player);
    }

    /**
     * @param nickname look for the nickname in the actual players
     * @return the player if is found, {@code null} otherwise
     */
    public Player getPlayerByName(String nickname){
        for(Player player: players){
            if(player.getPlayerName().equals(nickname)){
                return player;
            }
        }
        return null;
    }

    /**
     * To find the player with a specific TowerColor
     * @param towerColor of the player to be found
     * @return the player
     */
    public Player getPlayerByTowerColor(TowerColor towerColor){
        for(Player player: players){
            if(player.getColor().equals(towerColor)){
                return player;
            }
        }
        return null;
    }

    /**
     * To reset all the strategies to the standard ones
     * @return true when done
     */
    public boolean resetStrategies() {
        board.resetStrategy();
        hallManager.resetStrategy();
        return true;
    }

    public ActionType useCharacter(Player player, CharacterCard character) {
        // TODO
        int cost = character.getCost() + (character.hasCoinOn()?1:0);
        if(!character.hasCoinOn()) character.setCoinOn();
        if(bank.pay(player,cost))
            return character.getActionType();
        return null;
    }

    /**
     * Check if is possible to move motherNature of specified steps
     * @param steps that the player wants motherNature to do
     * @param player going to perform the action
     * @return true and perform the movement, false if not possible
     */
    public boolean moveMotherNature(int steps, Player player){
        // TODO: implement card to add 2 more possible steps, add a Mover class with a limit standard strategy etc.
        int maxMove = player.getLastPlayedAssistant().movement();
        if(steps <= maxMove && steps > 0) {
            board.moveMotherNature(steps, players);
            notifyObserver(new BoardNotification(board));
            return true;
        } else return false;
    }

    /**
     * Pick pawns from the chosen cloud and put it in player's entrance
     * @param player the player who chose
     * @param cloud the chosen cloud
     * @return true if adding to the entrance is ok, otherwise false
     */
    public boolean pickFromCloud(Player player, int cloud){
        Cloud cloudChosen = clouds.getSpecificCloud(cloud);
        if(cloudChosen != null){
            return player.addPawnsFromCloud(cloudChosen);
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
    public void playAssistant(Assistant assistant){
        currentPlayer.playAssistant(assistant);
        playerMapAssistantValue.put(currentPlayer,assistant.value());
        playerMapAssistantMovement.put(currentPlayer,assistant.movement());
    }

    /**
     *
     * @return list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @return the board of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     * @return the sack of the game
     */
    public Sack getSack() {
        return sack;
    }

    /**
     *
     * @return the clouds
     */
    public List<Cloud> getClouds() {
        return clouds.getClouds();
    }

    /**
     * @return the {@link GameLimit} of this {@link Game}
     */
    public GameLimit getGameLimit() {
        return gameLimit;
    }

    /**
     * @return the number of player that this game is created for
     */
    public int getNPlayers() {
        return nPlayers;
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

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    public ProfessorAssignor getProfessorAssignor(){
        return hallManager.getProfessorAssignor();
    }

    /**
     * @return true if the game is expert mode, false otherwise.
     */
    public boolean isExpertMode() {
        return isExpertMode;
    }

    public Bank getBank() {
        return bank;
    }

    public List<Wizard> getAlreadyChoiceWizard() {
        return alreadyChoiceWizard;
    }

    /**
     * Move a pawn from the entrance to the hall of the currentPlayer
     * @param pawnColor to be moved to the hall
     */
    public void moveFromEntranceToHall(PawnColor pawnColor) {
        currentPlayer.moveFromEntranceToHall(pawnColor);
        notifyObserver(new SchoolNotification(new ShortSchool(currentPlayer.getSchool()),currentPlayer.getPlayerName()));
    }

    /**
     * Move a pawn from the entrance of the current Player to a chosen island
     * @param pawnColor to be moved to the island
     * @param island the chosen island
     */
    public void moveFromEntranceToIsland(PawnColor pawnColor, int island) {
        currentPlayer.moveFromEntranceToIsland(new Pawns(pawnColor),board.getIslands().get(island));
        notifyObserver(new SchoolNotification(new ShortSchool(currentPlayer.getSchool()),currentPlayer.getPlayerName()));
        notifyObserver(new BoardNotification(board));
    }

    public List<TowerColor> getAlreadyChoiceTowerColor() {
        return alreadyChoiceTowerColor;
    }

    public Map<Player, Integer> getPlayerMapAssistantMovement() {
        return playerMapAssistantMovement;
    }

    public Map<Player, Integer> getPlayerMapAssistantValue() {
        return playerMapAssistantValue;
    }
}
