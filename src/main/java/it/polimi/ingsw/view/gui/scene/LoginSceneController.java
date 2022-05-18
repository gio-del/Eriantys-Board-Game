package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ClientObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginSceneController extends ClientObservable implements BasicSceneController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private Button confirmNicknameButton;

    @FXML
    private void initialize() {
        anchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            anchorPane.requestFocus();
            nicknameTextField.setPromptText("Insert Nickname");
        });
        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER))
                confirm();
        });
    }

    public void confirm() {
        if (nicknameTextField.getText().trim().equals("")) {
            SceneController.showAlert(Alert.AlertType.WARNING, "You must chose a nickname");
            return;
        }
        nicknameTextField.setDisable(true);
        confirmNicknameButton.setDisable(true);
        new Thread(() -> notifyObserver(obs -> obs.updateNickname(nicknameTextField.getText()))).start();
    }
}
