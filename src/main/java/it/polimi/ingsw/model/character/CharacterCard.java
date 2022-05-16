package it.polimi.ingsw.model.character;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.polimi.ingsw.model.Sack;
import it.polimi.ingsw.model.character.actiondata.ActionData;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.place.Place;

import java.util.ArrayList;
import java.util.List;

public class CharacterCard implements Place {
    @JsonIgnore
    private final Pawns students = new Pawns();
    private boolean banCard;
    private String name;
    private int cost;
    private String description;
    private ActionData action;
    private List<String> requires;
    @JsonIgnore
    private boolean coinOn = false;
    private int numberOfBanTiles;
    private int numberOfStudentsOn;
    @JsonIgnore
    private PawnColor chosenColor;
    private Island chosenIsland;
    private List<PawnColor> chosenSwap = new ArrayList<>();

    public CharacterCard() {
    }

    public CharacterCard(CharacterCard copy) {
        this.name = copy.name;
        this.cost = copy.cost;
        this.description = copy.description;
        this.action = copy.action; //todo does this need to be a copy?
        this.requires = copy.requires;
        this.numberOfStudentsOn = copy.numberOfStudentsOn;
        this.numberOfBanTiles = copy.numberOfBanTiles;
        this.banCard = copy.banCard;
    }

    @Override
    public boolean remove(Pawns pawns) {
        if (canBeRemoved(pawns)) {
            students.removePawns(pawns);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Pawns pawns) {
        if (canBeAdded(pawns)) {
            students.addPawns(pawns);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeAdded(Pawns pawns) {
        return numberOfStudentsOn > 0 && pawns.totalElements() + students.totalElements() <= numberOfStudentsOn;
    }

    @Override
    public boolean canBeRemoved(Pawns pawns) {
        return numberOfStudentsOn > 0 && students.canBeRemoved(pawns);
    }

    public boolean isBanCard() {
        return banCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasCoinOn() {
        return coinOn;
    }

    public void setCoinOn(boolean coinOn) {
        this.coinOn = coinOn;
    }

    public ActionData getAction() {
        return action;
    }

    public PawnColor getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(PawnColor chosenColor) {
        this.chosenColor = chosenColor;
    }

    public Island getChosenIsland() {
        return chosenIsland;
    }

    public void setChosenIsland(Island chosenIsland) {
        this.chosenIsland = chosenIsland;
    }

    public List<PawnColor> getChosenSwap() {
        return chosenSwap;
    }

    public void setChosenSwap(List<PawnColor> chosenSwap) {
        this.chosenSwap = chosenSwap;
    }


    public int getNumberOfStudentsOn() {
        return numberOfStudentsOn;
    }

    public Pawns getStudents() {
        return students;
    }

    public int getNumberOfBanTiles() {
        return numberOfBanTiles;
    }

    public void setNumberOfBanTiles(int numberOfBanTiles) {
        this.numberOfBanTiles = numberOfBanTiles;
    }

    public void addBanTiles() {
        this.numberOfBanTiles += 1;
    }

    public List<String> getRequires() {
        return requires;
    }

    public void fill(Sack sack) {
        for (int i = students.totalElements(); i < numberOfStudentsOn; i++) {
            students.addColor(sack.extract());
        }
    }
}
