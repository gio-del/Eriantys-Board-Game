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
import it.polimi.ingsw.view.gui.scene.ChooseWizardAndTCController;
import it.polimi.ingsw.view.gui.scene.PlayAssistantSceneController;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.Set;

/**
 * The graphical user interface implementation of the game.
 * This class is observed by the {@link ClientController}.
 * Gui communicates with the controller only with update() and it's a controller's job to communicate with server via network
 */
public class Gui extends ClientObservable implements View {

    @Override
    public void askConnectionInfo() {
        Platform.runLater(() -> SceneController.changeScene(observers, "connection.fxml"));
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
        ChooseWizardAndTCController controller = new ChooseWizardAndTCController(wizardsAvailable, colorsAvailable);
        observers.forEach(controller::addObserver);
        SceneController.changeScene(controller, "choose_wizard.fxml");
    }

    @Override
    public void chooseAssistant(Set<Assistant> playableAssistant) {
        PlayAssistantSceneController controller = new PlayAssistantSceneController();
        controller.setPlayableAssistant(playableAssistant);
        observers.forEach(controller::addObserver);
        SceneController.changeScene(controller, "play_assistant.fxml");
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
    public void showError(String msg) {
        Platform.runLater(() -> SceneController.showAlert(Alert.AlertType.ERROR, msg));
    }

    @Override
    public void win(String winner, boolean win) {
        //TODO
    }

    @Override
    public void showMessage(String msg) {
        Platform.runLater(() -> SceneController.showAlert(Alert.AlertType.INFORMATION, msg));
    }

    @Override
    public void injectResource(ShortModel resource) {
        //TODO
    }
}