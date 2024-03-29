package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;

import java.util.List;
import java.util.Set;

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
     * To choose a {@link Wizard} and a {@link TowerColor} from the ones available
     */
    void chooseWizardAndTowerColor(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable);

    /**
     * PLANNING PHASE:
     * To choose the assistant to play
     */
    void chooseAssistant(Set<Assistant> playableAssistant);

    /**
     * ACTION PHASE:
     * Move the students from the cloud to the hall
     *
     * @param clouds available to selected
     */
    void chooseCloud(List<ShortCloud> clouds);

    /**
     * ACTION PHASE:
     * Move the students from the entrance to the hall
     *
     * @param movableColor list of color that can be moved
     */
    void moveStudent(List<PawnColor> movableColor);

    /**
     * ACTION PHASE:
     * Move mother nature from the origin island to a target island
     * with a number of steps
     *
     * @param maximumSteps available for this turn for this player based on the assistant played
     */
    void moveMNature(int maximumSteps);

    void askColor();

    void askIsland();

    /**
     * update of the view
     */
    void updateScreen();

    /**
     * In case of a disconnection, the players still connected will receive a msg
     *
     * @param msg content
     */
    void showError(String msg);

    /**
     * When win happens
     */
    void win(String winner, boolean win);

    /**
     * Show a generic message received from server
     *
     * @param msg to be displayed
     */
    void showMessage(String msg);

    void injectResource(ShortModel resource);

    void askSwapList(int swap);

    void askConnectionInfo();
}
