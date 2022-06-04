package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * This class implements the controller of the initial scene
 */
public class HomeSceneController extends ClientObservable implements BasicSceneController {
    @FXML
    private Button button;

    /**
     * Initialization of the controller
     */
    @FXML
    private void initialize() {
        button.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                SceneManager.changeScene(observers, button.getScene(), "connection.fxml");
            }
        });
    }

    /**
     * Change the scene to the {@link ConnectionSceneController}
     */
    @FXML
    private void startLogin(MouseEvent event) {
        SceneManager.changeScene(observers, ((Node) event.getSource()).getScene(), "connection.fxml");
    }
}