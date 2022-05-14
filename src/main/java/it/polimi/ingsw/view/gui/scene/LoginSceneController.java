package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;

public class LoginSceneController extends ClientObservable implements BasicSceneController {
    private String nickname;

    @FXML
    private TextField nameField;

    @FXML
    public void confirm(ActionEvent event) {
        nickname = nameField.getText();
    }
}
