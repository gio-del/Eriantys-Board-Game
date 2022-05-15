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
import javafx.application.Platform;

import java.util.List;
import java.util.Set;

/**
 * The graphical user interface implementation of the game.
 * This class is observed by the {@link ClientController}.
 * Gui communicates with the controller only with update() and it's a controller's job to communicate with server via network
 */
public class Gui extends ClientObservable implements View {

    /**
     * Check if is a valid name
     *
     * @param nickname from input
     */
    public void checkNickName(String nickname) {
        if (nickname.length() > 0) {
            notifyObserver(observer -> observer.updateNickname(nickname));
        } else {
            //alertbox e richiedere il nickname.
        }
    }

    public void setNickname() {
        Platform.runLater(() -> SceneController.changeScene(observers, "login.fxml"));
    }

    @Override
    public void chooseGameMode() {
        Platform.runLater(() -> SceneController.changeScene(observers, "game_mode.fxml"));
    }

    @Override
    public void chooseWizardAndTowerColor(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable) {
        //TODO
    }

    @Override
    public void chooseAssistant(Set<Assistant> playableAssistant) {
        //TODO
    }

    @Override
    public void chooseCloud(List<ShortCloud> clouds) {
        //TODO
    }

    @Override
    public void moveStudent(List<PawnColor> movableColor) {
        //TODO
    }

    @Override
    public void moveMNature(int maximumSteps) {
        //TODO
    }

    @Override
    public void askColor() {
        //TODO
    }

    @Override
    public void askIsland() {
        //TODO
    }

    @Override
    public void askSwapList(int swap) {
        //TODO
    }

    @Override
    public void updateScreen(String nickname) {
        //TODO
    }

    @Override
    public void showDisconnection(String message) {
        //TODO
    }

    @Override
    public void win(String winner, boolean win) {
        //TODO
    }

    @Override
    public void showMessage(String msg) {
        //TODO
    }

    @Override
    public void injectResource(ShortModel resource) {
        //TODO
    }
}