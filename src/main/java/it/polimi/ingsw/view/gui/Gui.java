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
import it.polimi.ingsw.view.gui.scene.BoardSceneController;
import it.polimi.ingsw.view.gui.scene.ChooseWizardAndTCController;
import it.polimi.ingsw.view.gui.scene.LoginSceneController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;
import java.util.Set;

/**
 * The graphical user interface implementation of the game.
 * This class is observed by the {@link ClientController}.
 * Gui communicates with the controller only with update() and it's a controller's job to communicate with server via network
 */
public class Gui extends ClientObservable implements View {

    private ShortModel resource;
    private String nickname;

    @Override
    public void askConnectionInfo() {
        Platform.runLater(() -> SceneManager.changeScene(observers, "connection.fxml"));
    }

    public void setNickname() {
        LoginSceneController controller = new LoginSceneController(this);
        observers.forEach(controller::addObserver);
        Platform.runLater(() -> SceneManager.changeScene(controller, "login.fxml"));
    }

    @Override
    public void chooseGameMode() {
        Platform.runLater(() -> SceneManager.changeScene(observers, "game_mode.fxml"));
    }

    @Override
    public void chooseWizardAndTowerColor(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable) {
        ChooseWizardAndTCController controller = new ChooseWizardAndTCController(wizardsAvailable, colorsAvailable);
        observers.forEach(controller::addObserver);
        Platform.runLater(() -> SceneManager.changeScene(controller, "choose_wizard.fxml"));
    }

    @Override
    public void chooseAssistant(Set<Assistant> playableAssistant) {
        Platform.runLater(() -> {
            getBoardController().setPlayableAssistant(playableAssistant);
            ((Stage) SceneManager.getActualScene().getWindow()).setMaximized(true);
        });
    }

    @Override
    public void chooseCloud(List<ShortCloud> clouds) {
        Platform.runLater(() -> getBoardController().setSelectableClouds());
    }

    @Override
    public void moveStudent(List<PawnColor> movableColor) {
        Platform.runLater(() -> getBoardController().setMovableStudents());
    }

    @Override
    public void moveMNature(int maximumSteps) {
        Platform.runLater(() -> getBoardController().setMotherNatureMaximumSteps(maximumSteps));
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
    public void updateScreen() {
        Platform.runLater(() -> getBoardController().refresh());
    }

    @Override
    public void showError(String msg) {
        Platform.runLater(() -> {
            SceneManager.showAlert(Alert.AlertType.ERROR, msg);
            System.exit(0);
        });
    }

    @Override
    public void win(String winner, boolean win) {
        //TODO
    }

    @Override
    public void showMessage(String msg) {
        Platform.runLater(() -> SceneManager.showAlert(Alert.AlertType.INFORMATION, msg));
    }

    @Override
    public void injectResource(ShortModel resource) {
        this.resource = resource;
    }

    private BoardSceneController getBoardController() {
        BoardSceneController controller;
        try {
            controller = (BoardSceneController) SceneManager.getActualController();
        } catch (ClassCastException e) {
            controller = new BoardSceneController(resource, nickname);
            BoardSceneController finalController = controller;
            observers.forEach(finalController::addObserver);
            SceneManager.changeScene(controller, "board.fxml");
        }
        return controller;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}