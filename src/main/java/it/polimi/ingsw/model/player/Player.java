package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Place;
import it.polimi.ingsw.model.School;
import it.polimi.ingsw.model.pawns.Pawns;

import java.util.ArrayList;

import static it.polimi.ingsw.model.player.Assistant.*;

public class Player {
    private final int playerId;
    private final String playerName;
    private ArrayList<Assistant> hand = new ArrayList<>();
    private Wizard wizard;
    private int bank;
    private int towerNum;
    private TowerColor color;
    private Assistant lastPlayedAssistant;
    private School school = new School();

    public Player(int ID,String name,Wizard wizard,TowerColor color) {
        this.playerId = ID;
        this.playerName = name;
        this.wizard = wizard;
        this.color = color;
        bank = 0;
        towerNum = 8;  //6 in caso di partita a 3, biosgna gestire sta cosa

        hand.add(TURTLE);
        hand.add(ELEPHANT);
        hand.add(DOG);
        hand.add(OCTOPUS);
        hand.add(CROCODILE);
        hand.add(FOX);
        hand.add(EAGLE);
        hand.add(CAT);
        hand.add(OSTRICH);
        hand.add(LION);
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
        return lastPlayedAssistant.value;
    }

    public void move(Place from, Place to, Pawns pawns) {

    }

}