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

public class Player {
    private final String playerName;
    private ArrayList<Assistant> hand = new ArrayList<>();
    private Wizard wizard;
    private int bank;
    private int towerNum;
    private TowerColor color;
    private Assistant lastPlayedAssistant;
    private School school;

    public Player(String name,Wizard wizard,TowerColor color) {
        this.playerName = name;
        this.wizard = wizard;
        this.color = color;
        this.school = new School();
        bank = Constants.InitialCashPerPlayer;
        towerNum = 8;   //TODO: 3 players has 6 towers, if 4 players only 1 TeamMate have towers -> add TEAM ENUM?
        hand.addAll(Arrays.stream(values()).toList());
    }

    public String getPlayerName() {
        return playerName;
    }

    public Wizard getWizard() {
        return wizard;
    }

    public TowerColor getColor() {
        return color;
    }

    public int getTowerNum() {
        return towerNum;
    }

    public int getPlayerBank() {
        return bank;
    }

    public boolean isLastAssistant() {
        return hand.size() == 1;
    }

    public int playAssistant(Assistant assistant) {
        lastPlayedAssistant = assistant;
        hand.remove(assistant);
        return lastPlayedAssistant.value();
    }

    public Assistant getLastPlayedAssistant() {
        return lastPlayedAssistant;
    }

    public ArrayList<Assistant> getHand() {
        return hand;
    }

    public School getSchool() { return school; }

    public Pawns getEntrance() {
        return school.getEntrance();
    }

    public Pawns getHall() {
        return school.getHall();
    }

    public Pawns getProfTable() {
        return school.getProfessorTable();
    }

    public boolean moveFromEntranceToHall(Pawns pawns){
        return school.moveStudentToHall(pawns);   //TODO: add coin for 3,6,9 students of each color. hp: exception, move method in game, controller.
    }

    public boolean moveFromEntranceToIsland(Pawns pawns, Island island){
        if(school.removeStudentFromEntrance(pawns)){
            island.addStudent(pawns);
            return true;
        }
        return false;
    }

    public void setBank(int newBank) {
        bank = newBank;
    }

    public TowerColor addTowerToBoard(){
        towerNum = towerNum - 1;
        return color;
    }

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