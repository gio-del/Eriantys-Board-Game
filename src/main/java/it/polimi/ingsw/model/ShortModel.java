package it.polimi.ingsw.model;

import it.polimi.ingsw.model.character.ShortCharacter;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.place.ShortSchool;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.ShortPlayer;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a shorted version of the model that can be sent to the clients via network.
 */
public class ShortModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 8456994722346490452L;

    private Map<ShortPlayer, ShortSchool> schoolMap;
    private Map<String, Integer> moneyMap;
    private Map<String, Assistant> lastPlayedAssistantMap;
    private List<ShortCloud> clouds;
    private ShortBoard board;
    private Set<Wizard> wizardsAvailable;
    private Set<TowerColor> colorsAvailable;
    private Set<Assistant> playableAssistant;
    private List<PawnColor> pawnsAvailable;
    private List<ShortCharacter> characters;
    private int studentsInSack;

    public ShortModel(Game game, boolean expertMode) {
        this.clouds = game.getClouds().stream().map(ShortCloud::new).toList();
        this.schoolMap = new HashMap<>();
        this.lastPlayedAssistantMap = game.assistantMap();

        game.getPlayers().forEach(p -> schoolMap.put(new ShortPlayer(p), new ShortSchool(p.getSchool())));

        this.board = new ShortBoard(game.getBoard());
        this.studentsInSack = game.getSack().getNumberOfPawns();

        if (expertMode) {
            this.characters = game.getCharacterInUse().stream().map(ShortCharacter::new).toList();
            this.moneyMap = new HashMap<>();
            game.getPlayers().forEach(p -> moneyMap.put(p.getPlayerName(), game.getBank().getCashByPlayer(p)));
        }
    }

    public ShortModel() {
        this.schoolMap = new HashMap<>();
    }

    public Map<ShortPlayer, ShortSchool> getSchoolMap() {
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

    public void setWizardsAvailable(Set<Wizard> wizardsAvailable) {
        this.wizardsAvailable = wizardsAvailable;
    }

    public Set<TowerColor> getColorsAvailable() {
        return colorsAvailable;
    }

    public void setColorsAvailable(Set<TowerColor> colorsAvailable) {
        this.colorsAvailable = colorsAvailable;
    }

    public Set<Assistant> getPlayableAssistant() {
        return playableAssistant;
    }

    public void setPlayableAssistant(Set<Assistant> playableAssistant) {
        this.playableAssistant = playableAssistant;
    }

    public List<PawnColor> getPawnsAvailable() {
        return pawnsAvailable;
    }

    public void setPawnsAvailable(List<PawnColor> pawnsAvailable) {
        this.pawnsAvailable = pawnsAvailable;
    }

    public List<ShortCharacter> getCharacters() {
        return characters;
    }

    public int getStudentsInSack() {
        return studentsInSack;
    }

    public Map<String, Integer> getMoneyMap() {
        return moneyMap;
    }

    public Assistant getLastPlayedAssistant(String player) {
        return lastPlayedAssistantMap.get(player);
    }

    public Map<String, Assistant> getAssistantMap() {
        return this.lastPlayedAssistantMap;
    }

    public void updateClouds(List<ShortCloud> clouds) {
        this.clouds = clouds;
    }

    /**
     * Update the short model with the given info
     * @param model the delta shorted model
     */
    public void update(ShortModel model) {
        this.characters = model.characters;
        this.board = model.board;
        this.schoolMap = model.schoolMap;
        this.clouds = model.clouds;
        this.moneyMap = model.moneyMap;
        this.studentsInSack = model.studentsInSack;
        this.lastPlayedAssistantMap = model.lastPlayedAssistantMap;
    }
}
