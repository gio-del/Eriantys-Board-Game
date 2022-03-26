package it.polimi.ingsw.model;

import it.polimi.ingsw.model.character.CharactersDeck;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game instance;
    private ArrayList<Player> players;
    private final Board board;
    private final Sack sack;
    private final List<Cloud> clouds;
    private final CharactersDeck charactersDeck;
    private Player currentPlayer;
    private int generalBank;
    private List<Character> characterInUse;
    private final ProfessorAssignor professorAssignor;



    /**
     * The constuctor
     */
    public Game() {
        this.players = new ArrayList<>();
        this.board = new Board();
        this.sack = new Sack();
        this.clouds = new ArrayList<>();
        this.charactersDeck = new CharactersDeck();
        this.professorAssignor = new ProfessorAssignor();

    }

    /**
     *
     * @return singleton
     */
    public static synchronized Game getInstance(){
        if (instance == null)
            instance = new Game();
        return instance;
    }

    /**
     *
     * @param player new player to add in the game
     * @return True if ok
     */
    public void addPlayer(Player player){
    }

    /**
     *
     * @return the next player going to play
     */
    public Player nextPlayer(){

    }

    /**
     *
     * @param player to be removed
     * @return True if ok
     */
    public boolean removePlayer(Player player){

    }

    /**
     *
     * @param nickname look for the nickname in the actual players
     * @return the player if is found, {@code null} otherwise
     */
    public Player getPlayerByName(String nickname){

    }

    public int depositInBank(int deposit){

    }

    public boolean resetStrategies(){

    }

    public boolean moveFromEntranceToClass(){

    }

    public boolean useCharacter(Character character, String string){

    }

    public boolean moveMotherNature(int positions){

    }

    public Pawns pickFromCloud(Cloud cloud){

    }

    public boolean move(Player player, Place from, Place to, Pawns pawns){

    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public int getNumPlayer() {

    }

    public Sack getSack() {
        return sack;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public CharactersDeck getCharacterDeck() {
        return charactersDeck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getGeneralBank() {
        return generalBank;
    }

    public List<Character> getCharacterInUse() {
        return characterInUse;
    }

    public ProfessorAssignor getProfessorAssignor() {
        return professorAssignor;
    }
}
