package it.polimi.ingsw.model.character;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.model.Sack;
import it.polimi.ingsw.model.character.actiondata.ActionData;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.pawns.Pawns;
import it.polimi.ingsw.model.place.Island;
import it.polimi.ingsw.model.place.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterCard implements Place {
    private String name;
    private int cost;
    private String description;
    private ActionData action;
    private List<String> requires;
    @JsonIgnore
    private boolean coinOn = false;
    @JsonIgnore
    private final Map<Island,Integer> islandMapBan = new HashMap<>();
    private int numberOfBanTiles;
    private int numberOfStudentsOn;
    @JsonIgnore
    private final Pawns students = new Pawns();
    @JsonIgnore
    private PawnColor chosenColor;
    private Island chosenIsland;
    private List<PawnColor> chosenSwap = new ArrayList<>();

    @Override
    public boolean remove(Pawns pawns) {
        if(canBeRemoved(pawns)){
            students.removePawns(pawns);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Pawns pawns) {
        if(canBeMoved(pawns)){
            students.addPawns(pawns);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeMoved(Pawns pawns) {
        return numberOfStudentsOn>0 && pawns.totalElements()+students.totalElements() <= numberOfStudentsOn;
    }

    @Override
    public boolean canBeRemoved(Pawns pawns) {
        return numberOfStudentsOn>0 && students.canBeRemoved(pawns);
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }

    public ActionData getAction(){
        return action;
    }

    public PawnColor getChosenColor() {
        return chosenColor;
    }

    public Island getChosenIsland() {
        return chosenIsland;
    }

    public List<PawnColor> getChosenSwap() {
        return chosenSwap;
    }

    public void setChosenColor(PawnColor chosenColor) {
        this.chosenColor = chosenColor;
    }

    public void setChosenIsland(Island chosenIsland) {
        this.chosenIsland = chosenIsland;
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

    public Map<Island, Integer> getIslandMapBan() {
        return islandMapBan;
    }

    public int getNumberOfBanTiles() {
        return numberOfBanTiles;
    }

    public List<String> getRequires() {
        return requires;
    }

    public void fill(Sack sack) {
        for(int i=students.totalElements();i<numberOfStudentsOn;i++) {
            students.addColor(sack.extract());
        }
    }
}
