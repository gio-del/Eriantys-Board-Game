package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

public class ConnectionController extends ClientObservable implements BasicSceneController {
    private String ipPort;

    @FXML
    private TextField connectionTextField;

    @FXML
    private Button connectionButton;

    @FXML
    public void confirm(ActionEvent event) {
        ipPort = connectionTextField.getText();
        //controlla che sia valida e poi va avanti nel caso.
        //se va male allertBox, sa va bene cambia scena
        //va male TODO:
        //va bene
        SceneController.changeScene(connectionButton.getScene(), "/fxml/login.fxml");
    }
}