package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;

import java.util.List;

/**
 * View common interface, this can be implemented by various user interfaces (in our case GUI or CLI).
 */
public interface View {

    /**
     * To set the name of the player
     */
    void setNickname();

    /**
     * To choose the game mode to be played
     */
    void chooseGameMode();

    /**
     * To choose a wizard from the ones available
     */
    void chooseWizard(List<Wizard> wizardsAvailable);

    /**
     * To choose a TowerColor from the ones available
     */
    void chooseTowerColor(List<TowerColor> colorsAvailable);

    /**
     * PLANNING PHASE:
     * To choose the assistant to play
     */
    void chooseAssistant(List<Assistant> playerAssistant);

    /**
     * ACTION PHASE:
     * Move the students from the cloud to the hall
     * @param clouds available to selected
     */
    void chooseCloud(List<Cloud> clouds);

    /**
     * ACTION PHASE:
     * Move the students from the entrance to the hall
     * @param numberOfMovement refers to the number of movements available in the turn
     */
    void moveStudent(int numberOfMovement);

    /**
     * ACTION PHASE:
     * Move mother nature from the origin island to a target island
     * with a number of steps
     * @param maximumSteps available for this turn for this player based on the assistant played
     */
    void moveMNature(int maximumSteps);

    /**
     * ACTION PHASE:
     * Use a character if the characters are enabled
     * @param characterNotAlreadyPlayed can be played
     */
    void useCharacter(List<Character> characterNotAlreadyPlayed);

    /**
     * update of the view
     */
    void updateScreen();

    /**
     * In case of a disconnection, the players still connected will receive a message
     * @param message content
     */
    void disconnectionHandler(String message);

    /**
     * When win happens
     */
    void win(String winner, boolean win);
}
