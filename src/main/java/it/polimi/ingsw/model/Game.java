package it.polimi.ingsw.model;

import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.CharactersDeck;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Place;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.profassignment.ProfessorAssignor;
import javafx.scene.input.TouchEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private Pawns professors;
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
        sack.initialFill();
        board.initIslands(sack);
        sack.fill();
        this.generalBank = MaxNumOfCoins;
        this.clouds = new ArrayList<>();
        this.charactersDeck = new CharactersDeck();
        this.professorAssignor = new ProfessorAssignor();
        this.characterInUse = new ArrayList<>();
        this.professors = new Pawns();
        for(PawnColor pawnColor: PawnColor.values()){
            professors.addColor(pawnColor);
        }
        alreadyChoiceWizard.clear();
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
            generalBank--;
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
     * @return True if ok
     */
    public boolean removePlayer(Player player){
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

    public boolean resetStrategies(){

        return true;
    }

    public boolean useCharacter(Character character, String string){
        // TODO
        return true;
    }

    public Boolean moveMotherNature(int positions, Player player){
        int maxMove = player.getLastPlayedAssistant().movement();
        if(positions <= maxMove && positions > 0){
            board.moveMotherNature(positions, players);
            return true;
        } else { return false; }
    }

    public boolean addCoin(Player player, int coins) {
        if (generalBank >= coins) {
            int newAmount = player.getPlayerBank() + coins;
            player.setBank(newAmount);
            generalBank -= coins;
            return true;
        }
        return false;
    }

    public boolean removeCoin(Player player, int coins) {
        int newAmount = player.getPlayerBank() - coins;
        if (newAmount>0) {
            player.setBank(newAmount);
            generalBank += coins;
            return true;
        }
        return false;
    }

    public Pawns pickFromCloud(Cloud cloud){
        return cloud.getStudentsAndRemove();
    }

    public boolean move(Player player, Place from, Place to, Pawns pawns) {
        // TODO
        return true;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public int getNumPlayer() {
        return players.size();
    }

    public Sack getSack() {
        return sack;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }

    public void removeProfessorGame(PawnColor pawnColor){
        professors.removeColor(pawnColor);
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

    public List<CharacterCard> getCharacterInUse() {
        return characterInUse;
    }

    public ProfessorAssignor getProfessorAssignor() {
        return professorAssignor;
    }

    public Pawns getProfessors() {
        return professors;
    }

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }
}
