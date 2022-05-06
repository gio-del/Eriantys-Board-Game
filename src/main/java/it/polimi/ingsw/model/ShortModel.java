package it.polimi.ingsw.model;

import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a shorted version of the model that the client has.
 */
public class ShortModel {
    private final Map<String, ShortSchool> schoolMap;
    private List<ShortCloud> clouds;
    private ShortBoard board;
    private Set<Wizard> wizardsAvailable;
    private Set<TowerColor> colorsAvailable;
    private Set<Assistant> playableAssistant;
    private List<PawnColor> pawnsAvailable;

    public ShortModel() {
        this.schoolMap = new HashMap<>();
    }

    public Map<String, ShortSchool> getSchoolMap() {
        return schoolMap;
    }

    public List<ShortCloud> getClouds() {
        return clouds;
    }

    public ShortBoard getBoard() {
        return board;
    }

    public Set<Wizard> getWizardsAvailable() {
        return wizardsAvailable;
    }

    public Set<TowerColor> getColorsAvailable() {
        return colorsAvailable;
    }

    public Set<Assistant> getPlayableAssistant() {
        return playableAssistant;
    }

    public List<PawnColor> getPawnsAvailable() {
        return pawnsAvailable;
    }

    public void updateSchool(ShortSchool school, String owner) {
        this.schoolMap.put(owner,school);
    }

    public void updateCloud(List<ShortCloud> clouds) {
        this.clouds = clouds;
    }

    public void updateBoard(ShortBoard board) {
        this.board = board;
    }

    public void setWizardsAvailable(Set<Wizard> wizardsAvailable) {
        this.wizardsAvailable = wizardsAvailable;
    }

    public void setColorsAvailable(Set<TowerColor> colorsAvailable) {
        this.colorsAvailable = colorsAvailable;
    }

    public void setPlayableAssistant(Set<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
    }

    public void setPawnsAvailable(List<PawnColor> pawnsAvailable) {
        this.pawnsAvailable = pawnsAvailable;
    }
}
