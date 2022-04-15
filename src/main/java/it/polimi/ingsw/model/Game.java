package it.polimi.ingsw.model;

import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.CharactersDeck;
import it.polimi.ingsw.model.place.BankHallObserver;
import it.polimi.ingsw.model.place.HallObserver;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents Eriantys game
 */
public class Game {
    private final List<Player> players;
    private final Board board;
    private final Sack sack;
    private final List<Cloud> clouds;
    private Player currentPlayer;
    private final List<Wizard> alreadyChoiceWizard;
    private final HallObserver hallObserver;
    private final GameLimit gameLimit;
    private final int nPlayers;
    private final boolean isExpertMode;
    private List<CharacterCard> characterInUse;

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
        this.clouds = new ArrayList<>();
        for(int i=0;i<nPlayers;i++){
            clouds.add(new Cloud());
        }
        this.alreadyChoiceWizard = new ArrayList<>();
        if(isExpertMode)
            this.hallObserver = new HallObserver();
        else
            this.hallObserver = new BankHallObserver();
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
                this.players.size() >= this.nPlayers) {
            return false;
        }
        else{
            // TODO: check Builder pattern for Player class
            Player player = new Player(name,wizard,towerColor,gameLimit,hallObserver);
            alreadyChoiceWizard.add(player.getWizard());
            this.players.add(player);
            this.hallObserver.addPlayer(player);
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
     *
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
    public boolean resetStrategies(){
        board.resetStrategy();
        hallObserver.resetStrategy();
        return true;
    }

    public boolean useCharacter(Character character, String string){
        // TODO: handle character use
        return true;
    }

    /**
     * Check if is possible to move motherNature of specified steps
     * @param steps that the player wants motherNature to do
     * @param player going to perform the action
     * @return true and perform the movement, false if not possible
     */
    public boolean moveMotherNature(int steps, Player player){
        // TODO: implement card to add 2 more possible steps
        int maxMove = player.getLastPlayedAssistant().movement();
        if(steps <= maxMove && steps > 0){
            board.moveMotherNature(steps, players);
            return true;
        } else return false;
    }

    /**
     * Pick pawns from the chosen cloud and put it in player's entrance
     * @param player the player who chose
     * @param cloud the chosen cloud
     * @return true if adding to the entrance is ok, otherwise false
     */
    public boolean pickFromCloud(Player player, Cloud cloud){
        //TODO: check the usage of this method, controller use Player or just the name of the Player?
        return player.addPawnsFromCloud(cloud);
    }

    /**
     * Fill the clouds according to the game limit, this is also used at the beginning of each turn
     */
    public void fillClouds() {
        for(Cloud cloud: clouds){
            cloud.fill(sack.extractListOfPawns(gameLimit.getStudentOnCloud()));
        }
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
        return clouds;
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
     *
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
        return hallObserver.getProfessorAssignor();
    }

    public boolean isExpertMode() {
        return isExpertMode;
    }
}
