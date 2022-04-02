package it.polimi.ingsw.model;

import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.CharactersDeck;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;

import java.util.ArrayList;
import java.util.List;
import static it.polimi.ingsw.constants.Constants.MaxNumOfCoins;

public class Game {
    private static Game instance;
    private final List<Player> players;
    private final Board board;
    private final Sack sack;
    private final List<Cloud> clouds;
    private final CharactersDeck charactersDeck;
    private Player currentPlayer;
    private int generalBank;
    private List<CharacterCard> characterInUse;
    private final ProfessorAssignor professorAssignor;
    private static final List<Wizard> alreadyChoiceWizard = new ArrayList<>();


    /**
     * The private constructor
     */
    private Game() {
        this.players = new ArrayList<>();
        this.board = new Board();
        this.sack = new Sack();
        this.generalBank = MaxNumOfCoins;
        this.clouds = new ArrayList<>();
        this.charactersDeck = new CharactersDeck();
        this.professorAssignor = new ProfessorAssignor();
        this.characterInUse = new ArrayList<>();
        alreadyChoiceWizard.clear();
        resetStrategies();
    }

    /**
     *
     * @return instance of Game
     */
    public static Game getInstance(){
        if (instance == null)
            instance = new Game();
        return instance;
    }

    /**
     * reset instance of Game
     */
    public static void resetInstance(){
        instance = null;
    }


    /**
     * initialize game
     */
    public void init(){
        CharactersDeck charactersDeck = new CharactersDeck();
        charactersDeck.init();
        characterInUse = charactersDeck.extractCharacterInUse();
        sack.initialFill();
        sack.fill();
    }

    /**
     *
     * @param player new player to add in the game
     * @return True if ok
     */
    public boolean addPlayer(Player player){
        if(this.players.contains(player) || alreadyChoiceWizard.contains(player.getWizard())) {
            return false;
        }
        else{
            alreadyChoiceWizard.add(player.getWizard());
            this.players.add(player);
            generalBank -= 1;
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


    public void depositInBank(int deposit){
        this.generalBank += deposit;
    }

    /**
     * To reset all the strategies to the standard ones
     * @return true when done
     */
    public boolean resetStrategies(){
        board.resetStrategy();
        professorAssignor.resetStrategy();
        return true;
    }

    public boolean useCharacter(Character character, String string){
        // TODO
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
     * To add a coin to a specific Player
     * @param player that is receiving the amount
     * @param coins is the amount
     * @return true if there is enough coins to be given, false otherwise
     */
    public boolean addCoin(Player player, int coins) {
        if (generalBank >= coins) {
            int newAmount = player.getPlayerBank() + coins;
            player.setBank(newAmount);
            generalBank -= coins;
            return true;
        }
        return false;
    }

    /**
     * To remove a coin from a specific Player
     * @param player that spend the amount
     * @param coins is the amount
     * @return true if the player ha enough money, false otherwise
     */
    public boolean removeCoin(Player player, int coins) {
        int newAmount = player.getPlayerBank() - coins;
        if (newAmount>0) {
            player.setBank(newAmount);
            generalBank += coins;
            return true;
        }
        return false;
    }

    /**
     * To pick pawns from a cloud
     * @param cloud chosen
     * @return the pawns of the cloud
     */
    public Pawns pickFromCloud(Cloud cloud){
        return cloud.getStudentsAndRemove();
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
     *
     * @return the Deck of the characters
     */
    public CharactersDeck getCharacterDeck() {
        return charactersDeck;
    }

    /**
     *
     * @return the current playing character
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *
     * @return the amount in the general bank
     */
    public int getGeneralBank() {
        return generalBank;
    }

    /**
     *
     * @return the character available in this game
     */
    public List<CharacterCard> getCharacterInUse() {
        return characterInUse;
    }


    public ProfessorAssignor getProfessorAssignor() {
        return professorAssignor;
    }

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

}
