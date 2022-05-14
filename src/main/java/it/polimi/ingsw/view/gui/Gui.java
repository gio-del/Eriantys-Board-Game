package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.client.ClientController;
import it.polimi.ingsw.model.ShortModel;
import it.polimi.ingsw.model.clouds.ShortCloud;
import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.TowerColor;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.View;

import java.util.List;
import java.util.Set;

/**
 * The graphical user interface implementation of the game.
 * This class is observed by the {@link ClientController}.
 * Gui communicates with the controller only with update() and it's a controller's job to communicate with server via network
 */
public class Gui extends ClientObservable implements View {
    private int maxSteps;
    private PawnColor chosenColor;
    private ShortModel resource;

    public void init() {
        //set scena per chiedere connessione
        //askConnectionInfo();
    }

    public void askConnection() {
        //chiedere ip port
    }

    /**
     * Check if is a valid name
     *
     * @param nickname from input
     */
    public void checkNickName(String nickname) {
        if (nickname.length() > 0) {
            notifyObserver(observer -> observer.updateNickname(nickname));
        } else {
            //allertbox e richiedere il nickname.
        }
    }

    public void setNickname() {

    }

    @Override
    public void chooseGameMode() {

    }

    @Override
    public void chooseWizardAndTowerColor(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable) {

    }

    @Override
    public void chooseAssistant(Set<Assistant> playableAssistant) {

    }

    @Override
    public void chooseCloud(List<ShortCloud> clouds) {

    }

    @Override
    public void moveStudent(List<PawnColor> movableColor) {

    }

    @Override
    public void moveMNature(int maximumSteps) {

    }

    @Override
    public void askColor() {

    }

    @Override
    public void askIsland() {

    }

    @Override
    public void updateScreen(String nickname) {

    }

    @Override
    public void showDisconnection(String message) {

    }

    @Override
    public void win(String winner, boolean win) {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void injectResource(ShortModel resource) {

    }
}