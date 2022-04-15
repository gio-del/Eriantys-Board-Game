package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.GameLimit;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.HallObserver;
import it.polimi.ingsw.model.place.School;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static it.polimi.ingsw.model.player.Assistant.*;

/**
 * This class represents the Player, the main actor of the game, to player is associated a {@link School}.
 */
public class Player {
    private final String playerName;
    private final ArrayList<Assistant> hand = new ArrayList<>();
    private final Wizard wizard;
    private Assistant lastPlayedAssistant;
    private final School school;

    public Player(String name, Wizard wizard, TowerColor towerColor, GameLimit gameLimit, HallObserver hallObserver) {
        this.playerName = name;
        this.wizard = wizard;
        this.school = new School(towerColor, gameLimit, hallObserver);
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
        return school.getTowerColor();
    }

    /**
     * This method return player's Tower number.
     *
     * @return player's tower number.
     */
    public int getTowerNum() {
        return school.getTowerNum();
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
    public List<Assistant> getHand() {
        return hand;
    }

    /**
     * This method move a group of {@link Pawns} (students) from player's Entrance to player's Hall (in {@link School}).
     *
     * @param pawns group of students to move.
     * @return false if the operation is invalid, true otherwise.
     */
    public boolean moveFromEntranceToHall(Pawns pawns){
        return school.moveStudentToHall(pawns);
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

    public boolean addPawnsFromCloud(Cloud cloud){
        return school.addStudentInEntrance(cloud.getStudents());
    }

    /**
     * This method add {@code 1} tower to an {@link Island} and remove it from player.
     *
     * @return color of the Player's towers.
     */
    public TowerColor addTowerToIsland(){
        return school.removeTower();
    }

    /**
     * This method add {@code 1} tower to player (previously removed from {@link Island}).
     */
    public void backTowerToPlayer(){
        school.addTower();
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
        return Objects.hash(playerName);
    }

    @Override
    public String toString() {
        return playerName + ", wizard= " + wizard + ", TowerColor= " + getColor();
    }

    /**
     * This method initialize {@link Player}'s hall with the correct number of pawn.
     * @param pawns to extract from
     */
    public void initialEntranceFill(Pawns pawns) {
        getSchool().addStudentInEntrance(pawns);
    }

}