package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeSceneController extends ClientObservable implements BasicSceneController {

    @FXML
    Button homeButton;

    @FXML
    public void startLogin(ActionEvent event) {
        SceneController.changeScene(homeButton.getScene(),"/fxml/connection.fxml");
    }
}