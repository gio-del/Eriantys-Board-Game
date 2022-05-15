package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

import java.awt.*;

public class LoginSceneController extends ClientObservable implements BasicSceneController {
    private String nickname;

    @FXML
    private TextField loginTextField;

    @FXML
    private Button loginButton;

    @FXML
    public void confirm(ActionEvent event) {
        nickname = loginTextField.getText();
        //controlla che sia valido e poi va avanti nel caso.
        //se va male allertBox, sa va bene cambia scena
        //va male TODO:
        //va bene
        SceneController.changeScene(loginButton.getScene(),"/fxml/game_mode.fxml");
    }
}
