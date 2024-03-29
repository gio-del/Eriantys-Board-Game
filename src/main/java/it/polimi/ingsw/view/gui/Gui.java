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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;
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

    /**
     * To set the name of the player
     */
    public void setNickname() {
        LoginSceneController controller = new LoginSceneController(this);
        observers.forEach(controller::addObserver);
        Platform.runLater(() -> SceneManager.changeScene(controller, "login.fxml"));
    }

    /**
     * To choose the game mode to be played
     */
    @Override
    public void chooseGameMode() {
        Platform.runLater(() -> SceneManager.changeScene(observers, "game_mode.fxml"));
    }

    /**
     * To choose a {@link Wizard} and a {@link TowerColor} from the ones available
     */
    @Override
    public void chooseWizardAndTowerColor(Set<Wizard> wizardsAvailable, Set<TowerColor> colorsAvailable) {
        ChooseWizardAndTCController controller = new ChooseWizardAndTCController(wizardsAvailable, colorsAvailable);
        observers.forEach(controller::addObserver);
        Platform.runLater(() -> SceneManager.changeScene(controller, "choose_wizard.fxml"));
    }

    /**
     * PLANNING PHASE:
     * To choose the assistant to play
     */
    @Override
    public void chooseAssistant(Set<Assistant> playableAssistant) {
        Platform.runLater(() -> getBoardController().setPlayableAssistant(playableAssistant));
    }

    /**
     * ACTION PHASE:
     * Move the students from the cloud to the hall
     *
     * @param clouds available to selected
     */
    @Override
    public void chooseCloud(List<ShortCloud> clouds) {
        Platform.runLater(() -> getBoardController().setSelectableClouds());
    }

    /**
     * ACTION PHASE:
     * Move the students from the entrance to the hall
     *
     * @param movableColor list of color that can be moved
     */
    @Override
    public void moveStudent(List<PawnColor> movableColor) {
        Platform.runLater(() -> getBoardController().setMovableStudents());
    }

    /**
     * ACTION PHASE:
     * Move mother nature from the origin island to a target island
     * with a number of steps
     *
     * @param maximumSteps available for this turn for this player based on the assistant played
     */
    @Override
    public void moveMNature(int maximumSteps) {
        Platform.runLater(() -> getBoardController().setMotherNatureMaximumSteps(maximumSteps));
    }

    /**
     * To ask a color to the player
     */
    @Override
    public void askColor() {
        Platform.runLater(() -> getBoardController().askColor());
    }

    /**
     * To ask an island to the player
     */
    @Override
    public void askIsland() {
        Platform.runLater(() -> getBoardController().askIsland());
    }

    /**
     * To ask the number of swaps to the player
     *
     * @param swap number of swaps
     */
    @Override
    public void askSwapList(int swap) {
        Platform.runLater(() -> getBoardController().askSwap(swap));
    }

    /**
     * update of the view
     */
    @Override
    public void updateScreen() {
        Platform.runLater(() -> getBoardController().refresh());
    }

    /**
     * In case of a disconnection, the players still connected will receive a msg
     *
     * @param msg content
     */
    @Override
    public void showError(String msg) {
        Platform.runLater(() -> {
            SceneManager.showAlert(Alert.AlertType.ERROR, msg);
            System.exit(0);
        });
    }

    /**
     * When win happens
     */
    @Override
    public void win(String winner, boolean win) {
        Platform.runLater(() -> {
            SceneManager.showAlert(Alert.AlertType.INFORMATION, win ? "YOU WON! Closing the game..." : winner + " won this game! Closing the game...");
            System.exit(0);
        });
    }

    /**
     * Show a generic message received from server
     *
     * @param msg to be displayed
     */
    @Override
    public void showMessage(String msg) {
        if (!isInBoardScene())
            Platform.runLater(() -> SceneManager.showAlert(Alert.AlertType.INFORMATION, msg));
        else
            Platform.runLater(() -> ((BoardSceneController) SceneManager.getActualController()).setInfoLabel(msg));
    }

    /**
     * To get the short-model
     */
    @Override
    public void injectResource(ShortModel resource) {
        this.resource = resource;
    }

    /**
     * Initialize the BoardController
     *
     * @return controller
     */
    private BoardSceneController getBoardController() {
        BoardSceneController controller;
        try {
            controller = (BoardSceneController) SceneManager.getActualController();
        } catch (ClassCastException e) {
            controller = new BoardSceneController(resource, nickname);
            observers.forEach(controller::addObserver);
            SceneManager.changeScene(controller, "board.fxml");
            ((Stage) SceneManager.getActualScene().getWindow()).setMaximized(true);
            SceneManager.getActualScene().getWindow().sizeToScene();
            ((Stage) SceneManager.getActualScene().getWindow()).setResizable(true);
            Media media = new Media(Objects.requireNonNull(getClass().getResource("/media/eriantys_sound.mp3")).toExternalForm());
            MediaPlayer player = new MediaPlayer(media);
            controller.setMediaPlayer(player);
            player.setAutoPlay(true);
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.setVolume(20);
            player.setOnEndOfMedia(() -> {
                player.seek(Duration.ZERO);
                player.play();
            });
        }
        return controller;
    }

    /**
     * @return true if is in BoardScene, false otherwise
     */
    private boolean isInBoardScene() {
        return SceneManager.getActualController() instanceof BoardSceneController;
    }

    /**
     * To set the name of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}