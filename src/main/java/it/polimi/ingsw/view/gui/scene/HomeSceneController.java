package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


public class HomeSceneController extends ClientObservable implements BasicSceneController {
    @FXML
    private Button button;

    @FXML
    private void startLogin(MouseEvent event) {
        SceneController.changeScene(observers, ((Node) event.getSource()).getScene(), "connection.fxml");
    }
}