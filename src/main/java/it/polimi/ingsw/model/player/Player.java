package it.polimi.ingsw.model.player;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.School;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import static it.polimi.ingsw.model.player.Assistant.*;

/**
 * This class represents the Player, the main actor of the game, to player is associated a {@link School}.
 */
public class Player {
    private final String playerName;
    private ArrayList<Assistant> hand = new ArrayList<>();
    private final Wizard wizard;
    private int bank;
    private int towerNum;
    private final TowerColor color;
    private Assistant lastPlayedAssistant;
    private final School school;

    public Player(String name,Wizard wizard,TowerColor color) {
        this.playerName = name;
        this.wizard = wizard;
        this.color = color;
        this.school = new School();
        bank = Constants.InitialCashPerPlayer;
        // TODO: move TowerNum to school
        towerNum = 8;   //TODO: 3 players has 6 towers, if 4 players only 1 TeamMate have towers -> add TEAM ENUM?
        hand.addAll(Arrays.stream(values()).toList());
    }

    /**
     * This method return player's name.
     *
     * @return player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * This method return player's {@link Wizard}.
     *
     * @return player's Wizard.
     */
    public Wizard getWizard() {
        return wizard;
    }

    /**
     * This method return player's {@link TowerColor}.
     *
     * @return player's color.
     */
    public TowerColor getColor() {
        return color;
    }

    /**
     * This method return player's Tower number.
     *
     * @return player's tower number.
     */
    public int getTowerNum() {
        return towerNum;
    }

    /**
     * This method return player's Bank.
     *
     * @return player's amount of coins.
     */
    public int getPlayerBank() {
        return bank;
    }

    /**
     * This method return player's {@link School}.
     *
     * @return the school associated to player.
     */
    public School getSchool() {
        return school;
    }

    /**
     * This method says if player has only one {@link Assistant} card remained.
     *
     * @return true if player has only {@code 1} card, false otherwise.
     */
    public boolean isLastAssistant() {
        return hand.size() == 1;
    }

    /**
     * This method permit to play one of the {@link Assistant} cards in player's hand.
     *
     * @param assistant Assistant played.
     * @return the played Assistant' value.
     */
    public int playAssistant(Assistant assistant) {
        lastPlayedAssistant = assistant;
        hand.remove(assistant);
        return lastPlayedAssistant.value();
    }

    /**
     * This method return player's last {@link Assistant} card played.
     *
     * @return the last Assistant card played.
     */
    public Assistant getLastPlayedAssistant() {
        return lastPlayedAssistant;
    }

    /**
     * This method return player's hand of {@link Assistant} cards.
     *
     * @return an ArrayList of player's Assistant cards.
     */
    public ArrayList<Assistant> getHand() {
        return hand;
    }

    /**
     * This method move a group of {@link Pawns} (students) from player's Entrance to player's Hall (in {@link School}).
     *
     * @param pawns group of students to move.
     * @return false if the operation is invalid, true otherwise.
     */
    public boolean moveFromEntranceToHall(Pawns pawns){
        return school.moveStudentToHall(pawns);   //TODO: add coin for 3,6,9 students of each color. hp: exception, move method in game, controller.
    }

    /**
     * This method move a group of {@link Pawns} (students) from player's Entrance (in {@link School}) to an {@link Island}.
     *
     * @param pawns group of students to move.
     * @param island destination.
     * @return false if the operation is invalid, true otherwise.
     */
    public boolean moveFromEntranceToIsland(Pawns pawns, Island island){
        if(school.removeStudentFromEntrance(pawns)){
            island.add(pawns);
            return true;
        }
        return false;
    }

    /**
     * This method reinitialize player's bank.
     *
     * @param newBank new bank value.
     */
    public void setBank(int newBank) {
        this.bank = newBank;
    }

    /**
     * This method add {@code 1} tower to an {@link Island} and remove it from player.
     *
     * @return color of the Player's towers.
     */
    public TowerColor addTowerToIsland(){
        towerNum = towerNum - 1;
        return color;
    }

    /**
     * This method add {@code 1} tower to player (previously removed from {@link Island}).
     */
    public void backTowerToPlayer(){
        towerNum = towerNum + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerName, player.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, wizard, color);
    }

    @Override
    public String toString() {
        return playerName + ", wizard= " + wizard + ", TowerColor= " + color;
    }
}