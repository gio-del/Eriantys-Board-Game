package it.polimi.ingsw.model.player;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.Place;
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
        towerNum = 8;  //TODO: if is the case of 3 player game this must be 6
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

    public Pawns getEntrance() {
        return school.getEntrance();
    }

    public Pawns getHall() {
        return school.getHall();
    }

    public Pawns getProfTable() {
        return school.getProfessorTable();
    }

    public void move(Place from, Place to, Pawns pawns) {
        //TODO
        //for(PawnColor pawnColor: PawnColor.values()) {
          //  for (int i=0;i<pawns.getFromColor(pawnColor);i++) {
            //    to.add(pawnColor);
              //  from.remove(pawnColor);
            //}
        //}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(playerName, player.playerName) || wizard == player.wizard || color == player.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, wizard, color);
    }

}